package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.scheduler.template.ScheduledJobTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/scheduler")
@PreAuthorize("hasRole('ADMIN')")
public class SchedulerRestController {
    private final Map<String, Runnable> jobs;

    @Autowired
    public SchedulerRestController(
            List<ScheduledJobTemplate> jobList
    ) {
        this.jobs = jobList.stream()
                .collect(Collectors.toUnmodifiableMap(
                        job -> job.getJobName().toLowerCase(),
                        job -> job::run
                ));
    }

    @GetMapping("/list")
    public Set<String> list() {
        return jobs.keySet();
    }

    @PostMapping("/trigger/{jobName}")
    public String triggerJob(@PathVariable(value = "jobName") String jobName) {
        if (StringUtils.isBlank(jobName)) {
            throw new InvalidDataException(Default.ERROR_NOT_PROVIDED_JOB);
        }

        final Runnable job = jobs.get(jobName.toLowerCase());

        if (null == job) {
            throw new InvalidDataException(Default.ERROR_NOT_FOUND_JOB);
        }

        job.run();

        return "Triggered: " + jobName;
    }
}