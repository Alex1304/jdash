package jdash.events.object;

import jdash.common.entity.GDLevel;

/**
 * Event emitted when a level is added to the Awarded category.
 *
 * @param addedLevel The data of the level that was added.
 */
public record AwardedAdd(GDLevel addedLevel) {}
