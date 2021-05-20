package jdash.client.response;

import jdash.client.request.GDRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * A {@link GDResponse} represents the result of the execution of a {@link GDRequest}. It abstracts whether the response
 * comes from the cache or the router.
 */
public interface GDResponse {

    /**
     * Deserializes the response using the given function. The function might not be invoked if the response was cached,
     * but will be used if it comes from a serialized source.
     *
     * @param deserializer the function that converts the raw response into a Java object
     * @param <E>          the type of the target Java object
     * @return a Mono emitting the deserialized response. It may also emit an exception if an exception is thrown from
     * within the deserializer function
     */
    <E> Mono<E> deserialize(Function<? super String, E> deserializer);
}
