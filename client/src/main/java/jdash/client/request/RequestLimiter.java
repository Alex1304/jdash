package jdash.client.request;

import java.time.Duration;
import java.util.Objects;

/**
 * Allows to limit the number of requests that can be processed within a specific timeframe. This class keeps track of
 * the invocations and their timestamp, and gives the remaining time before more permits can be delivered.
 */
public final class RequestLimiter {

    private static final RequestLimiter UNBOUNDED = new RequestLimiter(1, Duration.ZERO);

    private final int totalPermits;
    private final Duration resetInterval;
    private final Object lock = new Object();
    private final long[] permitHistory;
    private int tail, head, count;

    private RequestLimiter(int totalPermits, Duration resetInterval) {
        this.totalPermits = totalPermits;
        this.resetInterval = resetInterval;
        this.permitHistory = new long[totalPermits];
    }

    /**
     * A {@link RequestLimiter} that gives unlimited permits.
     *
     * @return an unlimited {@link RequestLimiter}
     */
    public static RequestLimiter none() {
        return RequestLimiter.UNBOUNDED;
    }

    /**
     * Creates a {@link RequestLimiter} with the given permits and reset interval values.
     *
     * @param permits       the number of times the action can be executed within the interval
     * @param resetInterval the interval after which the number of permits is reset for the action
     * @return a new {@link RequestLimiter}
     */
    public static RequestLimiter of(int permits, Duration resetInterval) {
        if (permits < 1) {
            throw new IllegalArgumentException("permits must be >= 1");
        }
        Objects.requireNonNull(resetInterval);
        return new RequestLimiter(permits, resetInterval);
    }

    /**
     * Consumes one permit in this limiter.
     */
    public void fire() {
        synchronized (lock) {
            if (remaining().getRemainingPermits() > 0) {
                permitHistory[head] = System.nanoTime();
                head = (head + 1) % permitHistory.length;
                count++;
            }
        }
    }

    /**
     * Gets the total number of times the action can be executed within the interval.
     *
     * @return the permits
     */
    public int getTotalPermits() {
        return totalPermits;
    }

    /**
     * Gets the interval after which a permit becomes available again after being used.
     *
     * @return the reset interval
     */
    public Duration getResetInterval() {
        return resetInterval;
    }

    /**
     * Returns the remaining permits and time before next permit, without consuming a permit.
     *
     * @return the remaining after computation
     */
    public Remaining remaining() {
        synchronized (lock) {
            long intervalNanos = resetInterval.toNanos();
            long now = System.nanoTime();
            while (count > 0 && now - permitHistory[tail] > intervalNanos) {
                tail = (tail + 1) % permitHistory.length;
                count--;
            }
            int permitsRemaining = totalPermits - count;
            Duration timeLeft = permitsRemaining == totalPermits ? Duration.ZERO
                    : Duration.ofNanos(resetInterval.toNanos() - System.nanoTime() + permitHistory[tail]);
            return new Remaining(permitsRemaining, timeLeft);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLimiter requestLimiter = (RequestLimiter) o;
        return totalPermits == requestLimiter.totalPermits && resetInterval.equals(requestLimiter.resetInterval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPermits, resetInterval);
    }

    @Override
    public String toString() {
        return "RequestLimiter{" +
                "totalPermits=" + totalPermits +
                ", resetInterval=" + resetInterval +
                '}';
    }

    /**
     * Data class containing info on the remaining permits and duration before reset.
     */
    public static final class Remaining {
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