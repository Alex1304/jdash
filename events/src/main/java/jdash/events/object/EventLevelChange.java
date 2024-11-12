package jdash.events.object;

import jdash.common.entity.GDDailyInfo;

/**
 * Event emitted when the Event level changes.
 *
 * @param before   The Event level info before the change.
 * @param after    The Event level info after the change.
 */
public record EventLevelChange(GDDailyInfo before, GDDailyInfo after) {}
