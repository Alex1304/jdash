package jdash.common.entity;

import org.immutables.value.Value;

import java.time.Duration;

/**
 * Represents information on a daily level or a weekly demon ("timely" being a generic term to refer to either of
 * them).
 */
@Value.Immutable
public interface GDTimelyInfo {

    /**
     * The number of the Daily level/Weekly demon. For example, a value of 1337 means it is the 1337th Daily
     * level/Weekly demon.
     *
     * @return a long
     */
    long number();

    /**
     * The time left until the Daily level/Weekly demon is renewed. This is a maximum duration: it is generally always
     * renewed when the duration is elapsed, but it may be renewed prematurely before the end of this duration.
     *
     * @return a {@link Duration}
     */
    Duration nextIn();
}
