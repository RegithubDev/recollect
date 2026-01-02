package com.resustainability.recollect.scheduler.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ScheduledJobTemplate {
    protected final Logger log;
    private final String jobName;
    private final AtomicBoolean running;

    private static final String LOG_SKIPPED = "Previous [{}] task still running. Skipping.";
    private static final String LOG_STARTED = "Started scheduled job [{}]";
    private static final String LOG_COMPLETED = "Completed scheduled job [{}] in {} ms";
    private static final String LOG_FAILED = "Scheduled job [{}] failed after {} ms";

    protected ScheduledJobTemplate() {
        this(false);
    }

    protected ScheduledJobTemplate(boolean isAtomicGuard) {
        this.log = LoggerFactory.getLogger(getClass());
        this.jobName = getClass().getSimpleName();
        this.running = isAtomicGuard ? new AtomicBoolean(false) : null;
    }

    protected abstract void executeJob();

    public String getJobName() {
        return jobName;
    }

    public void run() {
        if (null != running && !running.compareAndSet(false, true)) {
            log.info(LOG_SKIPPED, jobName);
            return;
        }
        final long start = System.currentTimeMillis();
        log.info(LOG_STARTED, jobName);
        try {
            executeJob();
            log.info(LOG_COMPLETED, jobName, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.error(LOG_FAILED, jobName, System.currentTimeMillis() - start, e);
        } finally {
            if (null != running) {
                running.set(false);
            }
        }
    }
}