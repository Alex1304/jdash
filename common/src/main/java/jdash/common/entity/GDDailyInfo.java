package jdash.common.entity;

import java.time.Duration;

/**
 * Represents information on a daily level or a weekly demon.
 *
 * @param number The number of the Daily level/Weekly demon. For example, a value of 1337 means it is the 1337th Daily
 *               level/Weekly demon.
 * @param nextIn The time left until the Daily level/Weekly demon is renewed. This is a maximum duration: it is
 *               generally always renewed when the duration is elapsed, but it may be renewed prematurely before the end
 *               of this duration.
 */
public record GDDailyInfo(long number, Duration nextIn) {}
