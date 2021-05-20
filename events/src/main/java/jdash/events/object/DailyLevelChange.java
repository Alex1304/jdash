package jdash.events.object;

import jdash.common.entity.GDTimelyInfo;
import org.immutables.value.Value;

/**
 * Event emitted when the Daily level changes.
 */
@Value.Immutable
public interface DailyLevelChange {

    /**
     * The Daily level info before the change.
     *
     * @return a {@link GDTimelyInfo}
     */
    @Value.Parameter
    GDTimelyInfo before();

    /**
     * The Daily level info after the change.
     *
     * @return a {@link GDTimelyInfo}
     */
    @Value.Parameter
    GDTimelyInfo after();
}
