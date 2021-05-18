package jdash.events.object;

import jdash.common.entity.GDTimelyInfo;
import org.immutables.value.Value;

@Value.Immutable
public interface WeeklyDemonChange {

    @Value.Parameter
    GDTimelyInfo before();

    @Value.Parameter
    GDTimelyInfo after();
}
