package jdash.events.producer;

import jdash.client.GDClient;
import jdash.events.object.*;
import reactor.core.publisher.Flux;

/**
 * Interface that abstracts how events are produced on each iteration of the loop. Even though this is a functional
 * interface and can technically be implemented with a lambda expression, {@link GDEventProducer} instances generally
 * need to be stateful so they can compute the events to emit according to the results of the previous iterations.
 */
@FunctionalInterface
public interface GDEventProducer {

    /**
     * An event producer that requests the first page of the Awarded category on each iteration in order to detect
     * changes. It may emit:
     * <ul>
     *     <li>{@link AwardedAdd} when it detects that a level was added to the Awarded category</li>
     *     <li>{@link AwardedRemove} when it detects that a level was removed from the Awarded category</li>
     *     <li>{@link AwardedUpdate} when it detects a change in the data of a level present in the Awarded
     *     category</li>
     * </ul>
     *
     * @return a {@link GDEventProducer}
     */
    static GDEventProducer awardedLevels() {
        return new AwardedEventProducer();
    }

    /**
     * An event producer that requests information on the current Daily level and Weekly demon on each iteration in
     * order to detect when the Daily level or the Weekly demon changes. It may emit:
     * <ul>
     *     <li>{@link DailyLevelChange} when it detects that the Daily level has changed</li>
     *     <li>{@link WeeklyDemonChange} when it detects that the Weekly demon has changed</li>
     * </ul>
     *
     * @return a {@link GDEventProducer}
     */
    static GDEventProducer timelyLevels() {
        return new TimelyEventProducer();
    }

    /**
     * Emits zero, one or more events by making requests using the given {@link GDClient}. The results of those requests
     * can be processed and memorized between two calls of this method in order to generate relevant events, for example
     * to detect changes between the results of two identical requests.
     *
     * @param client the client to use for requests
     * @return a Flux emitting zero, one or more events according to the results of the requests
     */
    Flux<Object> produce(GDClient client);
}
