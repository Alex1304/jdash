package jdash.events.object;

import jdash.common.entity.GDDailyInfo;

/**
 * Event emitted when the Daily level changes.
 *
 * @param before   The Daily level info before the change.
 * @param after    The Daily level info after the change.
 * @param isWeekly Whether this change is referring to the Daily level or the Weekly demon.
 */
public record DailyLevelChange(GDDailyInfo before, GDDailyInfo after, boolean isWeekly) {}
