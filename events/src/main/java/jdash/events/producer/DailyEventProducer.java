package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.entity.GDDailyInfo;
import jdash.events.object.DailyLevelChange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class DailyEventProducer implements GDEventProducer {

    private GDDailyInfo previousDaily;
    private GDDailyInfo previousWeekly;

    @Override
    public Flux<Object> produce(GDClient client) {
        var daily = client.getDailyLevelInfo()
                .flatMap(info -> {
                    var previousDaily = this.previousDaily;
                    this.previousDaily = info;
                    return Mono.justOrEmpty(previousDaily)
                            .filter(p -> p.number() < info.number())
                            .map(p -> new DailyLevelChange(p, info, false));
                });
        var weekly = client.getWeeklyDemonInfo()
                .flatMap(info -> {
                    var previousWeekly = this.previousWeekly;
                    this.previousWeekly = info;
                    return Mono.justOrEmpty(previousWeekly)
                            .filter(p -> p.number() < info.number())
                            .map(p -> new DailyLevelChange(p, info, true));
                });
        return Flux.concat(daily, weekly);
    }

}
