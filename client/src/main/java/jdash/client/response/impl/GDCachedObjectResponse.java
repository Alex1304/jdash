package jdash.client.response.impl;

import jdash.client.response.GDResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public final class GDCachedObjectResponse implements GDResponse {

    private final Object cached;

    public GDCachedObjectResponse(Object cached) {
        this.cached = cached;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Mono<E> deserialize(Function<? super String, E> deserializer) {
        return Mono.just((E) cached);
    }
}
