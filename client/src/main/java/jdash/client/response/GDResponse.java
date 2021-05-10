package jdash.client.response;

import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface GDResponse {

    <E> Mono<E> deserialize(Function<? super String, ? extends E> deserializer);
}
