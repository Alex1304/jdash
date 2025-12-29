package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.entity.GDDailyInfo;
import jdash.events.object.DailyLevelChange;
import org.jspecify.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class DailyEventProducer implements GDEventProducer {

    private @Nullable GDDailyInfo previousDaily;
    private @Nullable GDDailyInfo previousWeekly;

    @Override
    public Flux<Object> produce(GDClient client) {
        final var daily = client.getDailyLevelInfo()
                .flatMap(info -> {
                    final var previousDaily = this.previousDaily;
                    this.previousDaily = info;
                    return Mono.justOrEmpty(previousDaily)
                            .filter(p -> p.number() < info.number())
                            .map(p -> new DailyLevelChange(p, info, false));
                });
        final var weekly = client.getWeeklyDemonInfo()
                .flatMap(info -> {
                    final var previousWeekly = this.previousWeekly;
                    this.previousWeekly = info;
                    return Mono.justOrEmpty(previousWeekly)
                            .filter(p -> p.number() < info.number())
                            .map(p -> new DailyLevelChange(p, info, true));
                });
        return Flux.concat(daily, weekly);
    }

}
