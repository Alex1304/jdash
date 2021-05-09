package jdash.client.request;

import reactor.core.publisher.Mono;

import java.util.function.Function;

class GDCachedObjectResponse implements GDResponse {

    private final Object cached;

    GDCachedObjectResponse(Object cached) {
        this.cached = cached;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Mono<E> deserialize(Function<? super String, ? extends E> deserializer) {
        return Mono.just((E) cached);
    }
}
