package jdash.events.producer;

import jdash.client.GDClient;
import jdash.events.object.*;
import reactor.core.publisher.Flux;

/**
 * Interface that abstracts how events are produced on each iteration of the loop. Even though this is a functional
 * interface and can technically be implemented with a lambda expression, {@link GDEventProducer} instances generally
 * need to be stateful, so they can compute the events to emit according to the results of the previous iterations.
 */
@FunctionalInterface
public interface GDEventProducer {

    /**
     * Creates a {@link GDEventProducer} that periodically sends requests returning pages of results, and emits events
     * based on the comparison of these pages between each call.
     *
     * @param pagesComparator an implementation of {@link PagesComparator} that specifies how to fetch the pages and how
     *                        to emit events based on the detected changes.
     * @return a new {@link GDEventProducer}
     */
    static GDEventProducer comparingPages(PagesComparator<?> pagesComparator) {
        return new ComparingPagesEventProducer<>(pagesComparator);
    }

    /**
     * An event producer that requests the first pages of the Awarded levels category on each iteration in order to
     * detect changes. It may emit:
     * <ul>
     *     <li>{@link AwardedLevelAdd} when it detects that a level was added to the Awarded category</li>
     *     <li>{@link AwardedLevelRemove} when it detects that a level was removed from the Awarded category</li>
     *     <li>{@link AwardedLevelUpdate} when it detects a change in the data of a level present in the Awarded
     *     category</li>
     * </ul>
     *
     * @return a {@link GDEventProducer}
     */
    static GDEventProducer awardedLevels() {
        return new ComparingPagesEventProducer<>(new AwardedLevelPagesComparator());
    }

    /**
     * An event producer that requests the first pages of the Awarded lists category on each iteration in order to
     * detect changes. It may emit:
     * <ul>
     *     <li>{@link AwardedListAdd} when it detects that a list was added to the Awarded category</li>
     *     <li>{@link AwardedListRemove} when it detects that a list was removed from the Awarded category</li>
     *     <li>{@link AwardedListUpdate} when it detects a change in the data of a list present in the Awarded
     *     category</li>
     * </ul>
     *
     * @return a {@link GDEventProducer}
     */
    static GDEventProducer awardedLists() {
        return new ComparingPagesEventProducer<>(new AwardedListPagesComparator());
    }

    /**
     * An event producer that requests information on the current Daily level and Weekly demon on each iteration in
     * order to detect when the Daily level or the Weekly demon changes.
     *
     * @return a {@link GDEventProducer}
     */
    static GDEventProducer dailyLevels() {
        return new DailyEventProducer();
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
