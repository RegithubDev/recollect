package com.resustainability.recollect.scheduler;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.scheduler.template.ScheduledJobTemplate;
import com.resustainability.recollect.service.ScrapRegionAvailabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScrapRegionExtendAvailabilityJob extends ScheduledJobTemplate {
    private final Integer horizonMonths;
    private final ScrapRegionAvailabilityService scrapRegionAvailabilityService;

    @Autowired
    public ScrapRegionExtendAvailabilityJob(
            @Value("${app.region.horizonMonths:2}") Integer horizonMonths,
            ScrapRegionAvailabilityService scrapRegionAvailabilityService
    ) {
        this.horizonMonths = horizonMonths;
        this.scrapRegionAvailabilityService = scrapRegionAvailabilityService;
    }

    // Daily at 00:25 midnight
    @Scheduled(cron = "0 25 0 * * *", zone = Default.ZONE)
    public void scheduled() {
        super.run();
    }

    @Override
    public void executeJob() {
        scrapRegionAvailabilityService.extendAvailability(horizonMonths);
    }
}