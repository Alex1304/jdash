package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.entity.GDDailyInfo;
import jdash.events.object.EventLevelChange;
import org.jspecify.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class EventLevelProducer implements GDEventProducer {

    private @Nullable GDDailyInfo previousEventLevel;

    @Override
    public Flux<Object> produce(GDClient client) {
        return client.getEventLevelInfo()
                .flatMapMany(info -> {
                    final var previousEventLevel = this.previousEventLevel;
                    this.previousEventLevel = info;
                    return Mono.justOrEmpty(previousEventLevel)
                            .filter(p -> p.number() < info.number())
                            .map(p -> new EventLevelChange(p, info));
                });
    }

}
