package jdash.client.request;

import jdash.client.exception.HttpResponseException;
import org.reactivestreams.Subscription;
import reactor.core.Exceptions;
import reactor.core.publisher.*;
import reactor.core.scheduler.Scheduler;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

class GDRouterImpl extends BaseSubscriber<RequestWithCallback> implements GDRouter {

    private static final Logger LOGGER = Loggers.getLogger(GDRouterImpl.class);
    private static final Sinks.EmitFailureHandler RETRY_NON_SERIALIZED =
            (signalType, emitResult) -> emitResult == Sinks.EmitResult.FAIL_NON_SERIALIZED;

    private final RequestLimiter limiter;
    private final Duration timeout;
    private final Scheduler scheduler;
    private final HttpClient httpClient;
    private final Sinks.Many<RequestWithCallback> requestQueue = Sinks.many().multicast().onBackpressureBuffer();
    private Subscription subscription;

    public GDRouterImpl(RequestLimiter limiter, Duration timeout, String baseUrl, Scheduler scheduler) {
        this.limiter = limiter;
        this.timeout = timeout;
        this.scheduler = scheduler;
        var httpClient = HttpClient.create()
                .baseUrl(baseUrl)
                .headers(h -> {
                    h.add("Content-Type", "application/x-www-form-urlencoded");
                    h.add("User-Agent", "");
                });
        if (baseUrl.startsWith("https://")) {
            httpClient = httpClient.secure();
        }
        this.httpClient = httpClient;
        requestQueue.asFlux().subscribe(this);
    }

    @Override
    public Mono<String> send(GDRequest request) {
        var callback = Sinks.<String>one();
        var requestWithCallback = new RequestWithCallback(request, callback);
        for (; ; ) {
            var result = requestQueue.tryEmitNext(requestWithCallback);
            switch (result) {
                case FAIL_NON_SERIALIZED:
                    Thread.onSpinWait();
                    continue;
                case FAIL_TERMINATED:
                case FAIL_OVERFLOW:
                case FAIL_CANCELLED:
                case FAIL_ZERO_SUBSCRIBER:
                    return Mono.error(new Sinks.EmissionException(result, "Unable to push request to queue"));
            }
            var mono = callback.asMono().publishOn(scheduler);
            if (timeout != null) {
                return mono.timeout(timeout);
            }
            return mono;
        }
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    protected void hookOnNext(RequestWithCallback value) {
        limiter.fire();
        var request = value.request;
        var callback = value.callback;
        httpClient.doAfterRequest((httpClientRequest, connection) -> LOGGER.debug("Request sent: {}", request))
                .post()
                .uri(request.getUri())
                .send(ByteBufFlux.fromString(Flux.just(request.toRequestString())))
                .responseSingle(((httpClientResponse, byteBufMono) -> {
                    if (httpClientResponse.status().code() / 100 != 2) {
                        return Mono.error(new HttpResponseException(httpClientResponse.status()));
                    }
                    return byteBufMono.asString().defaultIfEmpty("");
                }))
                .retryWhen(Retry.backoff(10, Duration.ofMillis(100))
                        .filter(IOException.class::isInstance)
                        .doAfterRetry(retrySignal -> LOGGER.warn("Retried attempt {}/{} for failed request {} [{}]",
                                retrySignal.totalRetries(), 10, request, retrySignal.failure())))
                .onErrorMap(IOException.class, e -> Exceptions.retryExhausted("Giving up after 10 I/O failures", e))
                .doFinally(signalType -> {
                    if (subscription != null) {
                        var remaining = limiter.remaining();
                        if (remaining.getRemainingPermits() > 0) {
                            subscription.request(1);
                        } else {
                            Mono.delay(remaining.getTimeLeftBeforeNextPermit())
                                    .subscribe(__ -> subscription.request(1));
                        }
                    }
                })
                .subscribe(response -> callback.emitValue(response, RETRY_NON_SERIALIZED),
                        error -> callback.emitError(error, RETRY_NON_SERIALIZED),
                        () -> callback.emitEmpty(RETRY_NON_SERIALIZED));
    }

    @Override
    protected void hookFinally(SignalType type) {
        LOGGER.error("Request queue subscription has been terminated with signal {}", type);
    }

}

class RequestWithCallback {

    final GDRequest request;
    final Sinks.One<String> callback;

    RequestWithCallback(GDRequest request, Sinks.One<String> callback) {
        this.request = request;
        this.callback = callback;
    }
}