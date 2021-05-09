package jdash.client.request;

import reactor.core.publisher.Mono;

public interface GDRouter {

    Mono<String> send(GDRequest request);
}
