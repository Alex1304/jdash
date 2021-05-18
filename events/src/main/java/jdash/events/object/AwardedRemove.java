package jdash.events.object;

import jdash.common.entity.GDLevel;
import org.immutables.value.Value;

/**
 * Event emitted when a level is removed from the Awarded category.
 */
@Value.Immutable
public interface AwardedRemove {

    /**
     * The data of the level that was removed.
     *
     * @return a {@link GDLevel}
     */
    @Value.Parameter
    GDLevel removedLevel();
}
