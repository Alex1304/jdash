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
import java.util.UUID;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

class GDRouterImpl extends BaseSubscriber<RequestWithCallback> implements GDRouter {

    private static final Logger LOGGER = Loggers.getLogger(GDRouterImpl.class);

    private final RequestLimiter limiter;
    private final Duration timeout;
    private final Scheduler scheduler;
    private final HttpClient httpClient;
    private final Sinks.Many<RequestWithCallback> requestQueue = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<Duration> nextRequestScheduler = Sinks.many().multicast().onBackpressureBuffer();

    public GDRouterImpl(RequestLimiter limiter, Duration timeout, String baseUrl, Scheduler scheduler) {
        this.limiter = limiter;
        this.timeout = timeout;
        this.scheduler = scheduler;
        var httpClient = HttpClient.create()
                .baseUrl(baseUrl)
                .headers(h -> {
                    h.add("Content-Type", "application/x-www-form-urlencoded");
                    h.add("User-Agent", "");
                    h.add("Cookie", "gd=1;");
                });
        if (baseUrl.startsWith("https://")) {
            httpClient = httpClient.secure();
        }
        this.httpClient = httpClient;
        requestQueue.asFlux().subscribe(this);
        nextRequestScheduler.asFlux()
                .flatMap(Mono::delay)
                .subscribe(next -> request(1));
    }

    @Override
    public Mono<String> send(GDRequest request) {
        final var callback = Sinks.<String>one();
        final var requestWithCallback = new RequestWithCallback(request, callback);
        final var result = requestQueue.tryEmitNext(requestWithCallback);
        if (result.isSuccess()) {
            final var mono = callback.asMono().publishOn(scheduler);
            if (timeout != null) {
                return mono.timeout(timeout);
            }
            return mono;
        }
        return Mono.error(new Sinks.EmissionException(result));
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    protected void hookOnNext(RequestWithCallback value) {
        limiter.fire();
        final var request = value.request();
        final var callback = value.callback();
        final var requestId = UUID.randomUUID().toString();
        httpClient.doAfterRequest((httpClientRequest, connection) -> LOGGER
                        .debug("[requestId: {}] Request sent: {}", requestId, request))
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
                    final var remaining = limiter.remaining();
                    if (remaining.remainingPermits() > 0) {
                        request(1);
                    } else {
                        nextRequestScheduler.emitNext(remaining.timeLeftBeforeNextPermit(),
                                (result, emission) -> emission == Sinks.EmitResult.FAIL_NON_SERIALIZED);
                    }
                })
                .subscribe(response -> {
                            LOGGER.trace("[requestId: {}] Received response: {}", requestId, response);
                            callback.emitValue(response, FAIL_FAST);
                        },
                        error -> callback.emitError(error, FAIL_FAST),
                        () -> callback.emitEmpty(FAIL_FAST));
    }

    @Override
    protected void hookFinally(SignalType type) {
        LOGGER.error("Request queue subscription has been terminated with signal {}", type);
    }

}

record RequestWithCallback(GDRequest request, Sinks.One<String> callback) {}