package com.github.alex1304.jdash.cooldown;

import java.time.Duration;
import java.util.Objects;

/**
 * Represents the number of times an action can be executed within a specific time frame. The default implementation
 * given by {@link #of(int, Duration)} is thread-safe.
 */
public interface Cooldown {

    /**
     * A {@link Cooldown} that gives unlimited permits.
     *
     * @return an unlimited {@link Cooldown}
     */
    static Cooldown none() {
        return CooldownImpl.UNBOUNDED;
    }

    /**
     * Creates a {@link Cooldown} with the given permits and reset interval values.
     *
     * @param permits       the number of times the action can be executed within the interval
     * @param resetInterval the interval after which the number of permits is reset for the action
     * @return a new {@link Cooldown}
     */
    static Cooldown of(int permits, Duration resetInterval) {
        if (permits < 1) {
            throw new IllegalArgumentException("permits must be >= 1");
        }
        Objects.requireNonNull(resetInterval);
        return new CooldownImpl(permits, resetInterval);
    }

    /**
     * Consumes one permit in this cooldown for the specified user. If none is left, {@link CooldownException} is
     * thrown.
     *
     * @param userId the user id
     */
    void fire(long userId);

    /**
     * Gets the total number of times the action can be executed within the interval.
     *
     * @return the permits
     */
    int getTotalPermits();

    /**
     * Gets the interval after which a permit becomes available again after being used.
     *
     * @return the reset interval
     */
    Duration getResetInterval();

    /**
     * Computes and returns the remaining permits and time before next permit for the specified user.
     *
     * @param userId the user id
     * @return the remaining after computation
     */
    Remaining remaining(long userId);

    /**
     * Data class containing info on the remaining permits and duration before reset.
     */
    final class Remaining {
        private final int remainingPermits;
        private final Duration timeLeftBeforeNextPermit;

        public Remaining(int remainingPermits, Duration timeLeftBeforeNextPermit) {
            this.remainingPermits = remainingPermits;
            this.timeLeftBeforeNextPermit = timeLeftBeforeNextPermit;
        }

        /**
         * Gets the remaining number of permits.
         *
         * @return the remaining permits
         */
        public int getRemainingPermits() {
            return remainingPermits;
        }

        /**
         * Gets the amount of time left before the next increase of the number of permits. If the number of permits is
         * already at maximum, {@link Duration#ZERO} is returned.
         *
         * @return the time left before next permit
         */
        public Duration getTimeLeftBeforeNextPermit() {
            return timeLeftBeforeNextPermit;
        }
    }
}
