package jdash.client.response.impl;

import jdash.client.exception.GDClientException;
import jdash.client.exception.GDResponseException;
import jdash.client.exception.ResponseDeserializationException;
import jdash.client.request.GDRequest;
import jdash.client.response.GDResponse;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

public final class GDSerializedSourceResponse implements GDResponse {

    private final GDRequest request;
    private final Mono<String> serializedSource;
    private final Consumer<Object> onDeserialize;

    public GDSerializedSourceResponse(GDRequest request, Mono<String> serializedSource,
                                      Consumer<Object> onDeserialize) {
        this.request = request;
        this.serializedSource = serializedSource;
        this.onDeserialize = onDeserialize;
    }

    @Override
    public <E> Mono<E> deserialize(Function<? super String, E> deserializer) {
        return serializedSource
                .<E>handle((response, sink) -> {
                    try {
                        sink.next(deserializer.apply(response));
                    } catch (GDResponseException e) {
                        sink.error(e);
                    } catch (RuntimeException e) {
                        sink.error(new ResponseDeserializationException(response, e));
                    }
                })
                .onErrorMap(e -> new GDClientException(request, e))
                .doOnNext(onDeserialize);
    }
}
