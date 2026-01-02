package com.resustainability.recollect.scheduler;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.scheduler.template.ScheduledJobTemplate;
import com.resustainability.recollect.service.LocalBodyAvailabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LocalBodyExtendAvailabilityJob extends ScheduledJobTemplate {
    private final Integer horizonMonths;
    private final LocalBodyAvailabilityService localBodyAvailabilityService;

    @Autowired
    public LocalBodyExtendAvailabilityJob(
            @Value("${app.region.horizonMonths:2}") Integer horizonMonths,
            LocalBodyAvailabilityService localBodyAvailabilityService
    ) {
        this.horizonMonths = horizonMonths;
        this.localBodyAvailabilityService = localBodyAvailabilityService;
    }

    // Daily at 00:24 midnight
    @Scheduled(cron = "0 24 0 * * *", zone = Default.ZONE)
    public void scheduled() {
        super.run();
    }

    @Override
    public void executeJob() {
        localBodyAvailabilityService.extendAvailability(horizonMonths);
    }
}