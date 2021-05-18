package jdash.events;

import jdash.client.GDClient;
import jdash.events.producer.GDEventProducer;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.time.Duration;
import java.util.Set;

public class GDEventLoop {

    private static final Logger LOGGER = Loggers.getLogger(GDEventLoop.class);

    private final Sinks.Many<Object> eventEmitter;
    private final Disposable disposable;

    private GDEventLoop(Sinks.Many<Object> eventEmitter, Disposable disposable) {
        this.eventEmitter = eventEmitter;
        this.disposable = disposable;
    }

    public static GDEventLoop start(GDClient client, Set<GDEventProducer> eventProducers, Duration interval) {
        var eventEmitter = Sinks.many().multicast().onBackpressureBuffer();
        var disposable = Flux.interval(interval, Schedulers.boundedElastic())
                .flatMapIterable(__ -> eventProducers)
                .flatMap(producer -> producer.produce(client)
                        .onErrorResume(e -> Mono.fromRunnable(() -> LOGGER.error("Error while producing events", e))))
                .subscribe(object -> {
                    for (; ; ) {
                        Sinks.EmitResult result;
                        switch (result = eventEmitter.tryEmitNext(object)) {
                            case FAIL_NON_SERIALIZED:
                                Thread.onSpinWait();
                                continue;
                            case FAIL_TERMINATED:
                            case FAIL_CANCELLED:
                            case FAIL_ZERO_SUBSCRIBER:
                            case FAIL_OVERFLOW:
                                LOGGER.warn("Event dropped because of {}: {}", result, object);
                        }
                        return;
                    }
                });
        return new GDEventLoop(eventEmitter, disposable);
    }

    public void stop() {
        disposable.dispose();
        eventEmitter.emitComplete((signalType, emitResult) -> emitResult == Sinks.EmitResult.FAIL_NON_SERIALIZED);
    }

    public <T> Flux<T> on(Class<T> eventType) {
        return eventEmitter.asFlux().ofType(eventType).publishOn(Schedulers.boundedElastic());
    }
}
