package jdash.events.object;

import jdash.common.entity.GDTimelyInfo;
import org.immutables.value.Value;

@Value.Immutable
public interface DailyLevelChange {

    @Value.Parameter
    GDTimelyInfo before();

    @Value.Parameter
    GDTimelyInfo after();
}
