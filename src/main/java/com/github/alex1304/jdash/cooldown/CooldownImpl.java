package com.github.alex1304.jdash.cooldown;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

final class CooldownImpl implements Cooldown {

    static final Cooldown UNBOUNDED = new CooldownImpl(1, Duration.ZERO);

    private final int totalPermits;
    private final Duration resetInterval;
    private final ConcurrentHashMap<Long, Bucket> buckets = new ConcurrentHashMap<>();

    CooldownImpl(int totalPermits, Duration resetInterval) {
        this.totalPermits = totalPermits;
        this.resetInterval = resetInterval;
    }

    @Override
    public void fire(long userId) {
        buckets.computeIfAbsent(userId, k -> new Bucket()).fire();
    }

    @Override
    public int getTotalPermits() {
        return totalPermits;
    }

    @Override
    public Duration getResetInterval() {
        return resetInterval;
    }

    @Override
    public Remaining remaining(long userId) {
        return buckets.computeIfAbsent(userId, k -> new Bucket()).remaining();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CooldownImpl cooldown = (CooldownImpl) o;
        return totalPermits == cooldown.totalPermits && resetInterval.equals(cooldown.resetInterval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPermits, resetInterval);
    }

    @Override
    public String toString() {
        return "Cooldown{" +
                "totalPermits=" + totalPermits +
                ", resetInterval=" + resetInterval +
                '}';
    }

    private final class Bucket {

        private final Object lock = new Object();
        private final long[] permitHistory;
        private int tail, head, count;

        private Bucket() {
            this.permitHistory = new long[totalPermits];
        }

        private void fire() {
            synchronized (lock) {
                Remaining remaining = remaining();
                if (remaining.getRemainingPermits() == 0) {
                    throw new CooldownException(totalPermits, resetInterval, remaining.getTimeLeftBeforeNextPermit());
                }
                permitHistory[head] = System.nanoTime();
                head = (head + 1) % permitHistory.length;
                count++;
            }
        }

        private Remaining remaining() {
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
    }
}
