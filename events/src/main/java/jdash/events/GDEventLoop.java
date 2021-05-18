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
import reactor.util.annotation.Nullable;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

/**
 * The event loop is in charge of executing {@link GDEventProducer}s at regular intervals, and provides a centralized
 * way to subscribe to all the events emitted by them. The loop starts as soon as {@link Builder#buildAndStart()} is
 * called, and keeps running until {@link #stop()} is invoked. In order to avoid any inconsistencies when invoking the
 * producers repeatedly, the cache of the client is disabled/not used.
 */
public class GDEventLoop {

    /**
     * The default interval between two iterations of the loop, which is 1 minute.
     */
    public static final Duration DEFAULT_INTERVAL = Duration.ofMinutes(1);

    /**
     * The default set of event producers to use in the loop, which includes {@link GDEventProducer#awardedLevels()} and
     * {@link GDEventProducer#timelyLevels()}.
     */
    public static final Set<GDEventProducer> DEFAULT_PRODUCERS = Set.of(
            GDEventProducer.awardedLevels(),
            GDEventProducer.timelyLevels()
    );

    private static final Logger LOGGER = Loggers.getLogger(GDEventLoop.class);

    private final Sinks.Many<Object> eventEmitter;
    private final Disposable disposable;

    private GDEventLoop(Sinks.Many<Object> eventEmitter, Disposable disposable) {
        this.eventEmitter = eventEmitter;
        this.disposable = disposable;
    }

    /**
     * Creates a new {@link GDEventLoop} with the given client using default parameters ({@link #DEFAULT_PRODUCERS} and
     * {@link #DEFAULT_INTERVAL}) and starts it immediately.
     *
     * @param client the {@link GDClient} to pass to event producers so they can make requests
     * @return a new {@link GDEventLoop}
     */
    public static GDEventLoop startWithDefaults(GDClient client) {
        return builder(client).buildAndStart();
    }

    /**
     * Initializes a new builder with the given client.
     *
     * @param client the {@link GDClient} to pass to event producers so they can make requests
     * @return a new builder
     */
    public static Builder builder(GDClient client) {
        return new Builder(client);
    }

    /**
     * Stops the loop. The {@link Flux} returned by {@link #on(Class)} will complete and no more events will ever be
     * emitted. This method is idempotent: invoking it multiple times will have no effect past the first call.
     */
    public void stop() {
        disposable.dispose();
        eventEmitter.emitComplete((signalType, emitResult) -> emitResult == Sinks.EmitResult.FAIL_NON_SERIALIZED);
    }

    /**
     * Returns a {@link Flux} that allows to subscribe to the events emitted through this loop.
     *
     * @param eventType the type of event to filter on
     * @param <T>       the type of the event object
     * @return a {@link Flux}
     */
    public <T> Flux<T> on(Class<T> eventType) {
        return eventEmitter.asFlux().ofType(eventType).publishOn(Schedulers.boundedElastic());
    }

    public static class Builder {

        private final GDClient client;
        private Set<GDEventProducer> eventProducers;
        private Duration interval;

        private Builder(GDClient client) {
            this.client = Objects.requireNonNull(client);
        }

        /**
         * Sets the event producers to use. By default it uses {@link #DEFAULT_PRODUCERS}, but can be overridden here.
         *
         * @param eventProducers the event producers, or <code>null</code> to use default
         * @return this builder
         */
        public Builder setEventProducers(@Nullable Set<GDEventProducer> eventProducers) {
            this.eventProducers = eventProducers;
            return this;
        }

        /**
         * Sets the interval between two iterations of the loop. By default it uses {@link #DEFAULT_INTERVAL}, but can
         * be overridden here.
         *
         * @param interval the interval, or <code>null</code> to use default
         * @return this builder
         */
        public Builder setInterval(@Nullable Duration interval) {
            this.interval = interval;
            return this;
        }

        /**
         * Builds the {@link GDEventLoop} instance using the current state of this builder. The loop is started when the
         * instance is returned.
         *
         * @return a new {@link GDEventLoop}
         */
        public GDEventLoop buildAndStart() {
            var eventProducers = Objects.requireNonNullElse(this.eventProducers, DEFAULT_PRODUCERS);
            var interval = Objects.requireNonNullElse(this.interval, DEFAULT_INTERVAL);
            var eventEmitter = Sinks.many().multicast().onBackpressureBuffer();
            var disposable = Flux.interval(interval, Schedulers.boundedElastic())
                    .flatMapIterable(__ -> eventProducers)
                    .flatMap(producer -> producer.produce(client.withCacheDisabled())
                            .onErrorResume(e -> Mono.fromRunnable(
                                    () -> LOGGER.error("Error while producing events", e))))
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
    }
}
