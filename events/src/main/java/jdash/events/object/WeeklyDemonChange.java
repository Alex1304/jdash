package jdash.events.object;

import jdash.common.entity.GDTimelyInfo;
import org.immutables.value.Value;

/**
 * Event emitted when the Weekly demon changes.
 */
@Value.Immutable
public interface WeeklyDemonChange {

    /**
     * The Weekly demon info before the change.
     *
     * @return a {@link GDTimelyInfo}
     */
    @Value.Parameter
    GDTimelyInfo before();

    /**
     * The Weekly demon info after the change.
     *
     * @return a {@link GDTimelyInfo}
     */
    @Value.Parameter
    GDTimelyInfo after();
}
