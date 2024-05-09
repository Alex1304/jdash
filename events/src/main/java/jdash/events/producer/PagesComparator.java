package jdash.events.producer;

import jdash.client.GDClient;
import reactor.core.publisher.Flux;

import java.util.Optional;

/**
 * This interface facilitates the production of events based on the comparison of pages loaded at regular intervals
 * using a {@link GDClient}. Implementation instances can be passed to
 * {@link GDEventProducer#comparingPages(PagesComparator)} in order to create the corresponding
 * {@link GDEventProducer}.
 *
 * @param <T> the type of elements that the pages contain
 */
public interface PagesComparator<T> {

    /**
     * Defines how to fetch one page using the client. It should always account for the page number passed as parameter
     * when possible.
     *
     * @param client the client
     * @param page   the page number
     * @return a {@link Flux} emitting the elements that the page contains
     */
    Flux<T> fetchPage(GDClient client, int page);

    /**
     * Defines how to uniquely identify an element on a page. It will usually be the ID of said object.
     *
     * @param element the element
     * @return a unique ID of the element
     */
    long idGetter(T element);

    /**
     * Creates an "add" event object for the given element. Returning an empty optional will instruct the calling
     * {@link GDEventProducer} not to create any "add" event.
     *
     * @param element the element that was detected to be added
     * @return the event object, or an empty optional to indicate not to create an event
     */
    Optional<Object> createAddEvent(T element);

    /**
     * Creates a "remove" event object for the given element. Returning an empty optional will instruct the calling
     * {@link GDEventProducer} not to create any "remove" event.
     *
     * @param element the element that was detected to be removed
     * @return the event object, or an empty optional to indicate not to create an event
     */
    Optional<Object> createRemoveEvent(T element);

    /**
     * Creates a "update" event object for the given elements. Returning an empty optional will instruct the calling
     * {@link GDEventProducer} not to create any "update" event.
     *
     * @param oldElement the old version of the element that was detected to be updated
     * @param newElement the new version of the element that was detected to be updated
     * @return the event object, or an empty optional to indicate not to create an event
     */
    Optional<Object> createUpdateEvent(T oldElement, T newElement);
}
