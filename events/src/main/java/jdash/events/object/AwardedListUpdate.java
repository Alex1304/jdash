package jdash.events.object;

import jdash.common.entity.GDList;

/**
 * Event emitted when a change is detected in the data of a list present in the Awarded category.
 *
 * @param oldData The data of the list before the update.
 * @param newData The data of the list after the update.
 */
public record AwardedListUpdate(GDList oldData, GDList newData) {}
