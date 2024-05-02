package jdash.events.object;

import jdash.common.entity.GDList;

/**
 * Event emitted when a list is added to the Awarded category.
 *
 * @param addedList The data of the list that was added.
 */
public record AwardedListAdd(GDList addedList) {}
