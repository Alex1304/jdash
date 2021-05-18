package jdash.client.request;

import jdash.client.exception.HttpResponseException;
import org.reactivestreams.Subscription;
import reactor.core.publisher.*;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.time.Duration;

class GDRouterImpl implements GDRouter {

    private static final Logger LOGGER = Loggers.getLogger(GDRouterImpl.class);
    private static final Sinks.EmitFailureHandler RETRY_NON_SERIALIZED =
            (signalType, emitResult) -> emitResult == Sinks.EmitResult.FAIL_NON_SERIALIZED;

    private final RequestLimiter limiter;
    private final Duration timeout;
    private final HttpClient httpClient;
    private final Sinks.Many<RequestWithCallback> requestQueue = Sinks.many().multicast().onBackpressureBuffer();

    public GDRouterImpl(RequestLimiter limiter, Duration timeout, String baseUrl) {
        this.limiter = limiter;
        this.timeout = timeout;
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
        requestQueue.asFlux().subscribe(new RequestQueueSubscriber());
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
            if (timeout != null) {
                return callback.asMono().timeout(timeout);
            }
            return callback.asMono();
        }
    }

    private static class RequestWithCallback {

        private final GDRequest request;
        private final Sinks.One<String> callback;

        private RequestWithCallback(GDRequest request, Sinks.One<String> callback) {
            this.request = request;
            this.callback = callback;
        }
    }

    private class RequestQueueSubscriber extends BaseSubscriber<RequestWithCallback> {

        private Subscription subscription;

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
}
