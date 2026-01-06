package com.resustainability.recollect.commons;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class RateLimitedRefresher<T> {
    private final Duration debounce;
    private final Duration maxStaleness;
    private final Supplier<T> loader;

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private final ReentrantLock refreshLock = new ReentrantLock();
    private final AtomicBoolean refreshQueued = new AtomicBoolean(false);

    private volatile T cachedValue;
    private volatile Instant lastRefresh;

    public RateLimitedRefresher(
            Duration debounce,
            Duration maxStaleness,
            Supplier<T> loader
    ) {
        this.debounce = debounce;
        this.maxStaleness = maxStaleness;
        this.loader = loader;
    }

    public void requestRefresh() {
        if (!refreshQueued.compareAndSet(false, true)) {
            return;
        }

        scheduler.schedule(this::refreshIfNeeded,
                debounce.toMillis(), TimeUnit.MILLISECONDS);
    }

    public T get() {
        if (cachedValue == null) {
            refreshIfNeeded();
        }
        return cachedValue;
    }

    private void refreshIfNeeded() {
        if (!refreshLock.tryLock()) {
            return;
        }

        try {
            if (lastRefresh != null &&
                Duration.between(lastRefresh, Instant.now())
                        .compareTo(maxStaleness) < 0) {
                return;
            }

            cachedValue = loader.get();
            lastRefresh = Instant.now();
        } finally {
            refreshQueued.set(false);
            refreshLock.unlock();
        }
    }
}
