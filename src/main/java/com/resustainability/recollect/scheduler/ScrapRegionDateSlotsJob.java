package com.resustainability.recollect.scheduler;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.scheduler.template.ScheduledJobTemplate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScrapRegionDateSlotsJob extends ScheduledJobTemplate {
    public ScrapRegionDateSlotsJob() {}

    // Daily at 00:30 midnight
    @Scheduled(cron = "0 30 0 * * *", zone = Default.ZONE)
    public void scheduled() {
        super.run();
    }

    @Override
    public void executeJob() {

    }
}