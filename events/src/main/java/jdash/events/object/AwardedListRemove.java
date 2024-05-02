package jdash.events.object;

import jdash.common.entity.GDList;

/**
 * Event emitted when a list is removed from the Awarded category.
 *
 * @param removedList The data of the list that was removed.
 */
public record AwardedListRemove(GDList removedList) {}
