package jdash.events.producer;

import jdash.client.GDClient;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface GDEventProducer {

    Flux<Object> produce(GDClient client);
}
