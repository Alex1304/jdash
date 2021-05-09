package jdash.client.request;

import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

class GDSerializedSourceResponse implements GDResponse {

    private final Mono<String> serializedSource;
    private final Consumer<Object> onDeserialize;

    GDSerializedSourceResponse(Mono<String> serializedSource, Consumer<Object> onDeserialize) {
        this.serializedSource = serializedSource;
        this.onDeserialize = onDeserialize;
    }

    @Override
    public <E> Mono<E> deserialize(Function<? super String, ? extends E> deserializer) {
        return serializedSource.<E>map(deserializer).doOnNext(onDeserialize);
    }
}
