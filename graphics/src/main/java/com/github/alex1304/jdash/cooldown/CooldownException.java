package com.github.alex1304.jdash.cooldown;

import java.time.Duration;

/**
 * Thrown when an action is on cooldown.
 */
public final class CooldownException extends RuntimeException {

    private final long permits;
    private final Duration resetInterval;
    private final Duration retryAfter;

    public CooldownException(long permits, Duration resetInterval, Duration retryAfter) {
        super("Action on cooldown. Retry after: " + retryAfter);
        this.permits = permits;
        this.resetInterval = resetInterval;
        this.retryAfter = retryAfter;
    }

    /**
     * Gets the original number of permits of the cooldown.
     *
     * @return the permits
     */
    public long getPermits() {
        return permits;
    }

    /**
     * Gets the original reset interval of the cooldown.
     *
     * @return the reset interval
     */
    public Duration getResetInterval() {
        return resetInterval;
    }

    /**
     * Gets the duration after which it should be safe to execute the action again.
     *
     * @return a {@link Duration}
     */
    public Duration getRetryAfter() {
        return retryAfter;
    }
}
