package jdash.events.object;

import jdash.common.entity.GDLevel;

/**
 * Event emitted when a level is removed from the Awarded category.
 *
 * @param removedLevel The data of the level that was removed.
 */
public record AwardedLevelRemove(GDLevel removedLevel) {}
