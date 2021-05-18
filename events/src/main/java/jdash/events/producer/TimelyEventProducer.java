package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.entity.GDTimelyInfo;
import jdash.events.object.ImmutableDailyLevelChange;
import jdash.events.object.ImmutableWeeklyDemonChange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TimelyEventProducer implements GDEventProducer {

    private GDTimelyInfo previousDaily;
    private GDTimelyInfo previousWeekly;

    @Override
    public Flux<Object> produce(GDClient client) {
        var daily = client.getDailyLevelInfo()
                .flatMap(info -> {
                    var previousDaily = this.previousDaily;
                    this.previousDaily = info;
                    return Mono.justOrEmpty(previousDaily)
                            .filter(p -> p.number() < info.number())
                            .map(p -> ImmutableDailyLevelChange.of(p, info));
                });
        var weekly = client.getWeeklyDemonInfo()
                .flatMap(info -> {
                    var previousWeekly = this.previousWeekly;
                    this.previousWeekly = info;
                    return Mono.justOrEmpty(previousWeekly)
                            .filter(p -> p.number() < info.number())
                            .map(p -> ImmutableWeeklyDemonChange.of(p, info));
                });
        return Flux.concat(daily, weekly);
    }

}
