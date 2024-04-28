package jdash.events.object;

import jdash.common.entity.GDLevel;

/**
 * Event emitted when a change is detected in the data of a level present in the Awarded category.
 *
 * @param oldData The data of the level before the update.
 * @param newData The data of the level after the update.
 */
public record AwardedUpdate(GDLevel oldData, GDLevel newData) {}
