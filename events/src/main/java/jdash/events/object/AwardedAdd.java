package jdash.events.object;

import jdash.common.entity.GDLevel;
import org.immutables.value.Value;

/**
 * Event emitted when a level is added to the Awarded category.
 */
@Value.Immutable
public interface AwardedAdd {

    /**
     * The data of the level that was added.
     *
     * @return a {@link GDLevel}
     */
    @Value.Parameter
    GDLevel addedLevel();
}
