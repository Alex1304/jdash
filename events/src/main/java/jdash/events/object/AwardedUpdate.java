package jdash.events.object;

import jdash.common.entity.GDLevel;
import org.immutables.value.Value;

/**
 * Event emitted when a change is detected in the data of a level present in the Awarded category.
 */
@Value.Immutable
public interface AwardedUpdate {

    /**
     * The data of the level before the update.
     *
     * @return a {@link GDLevel}
     */
    @Value.Parameter
    GDLevel oldData();

    /**
     * The data of the level after the update.
     *
     * @return a {@link GDLevel}
     */
    @Value.Parameter
    GDLevel newData();
}
