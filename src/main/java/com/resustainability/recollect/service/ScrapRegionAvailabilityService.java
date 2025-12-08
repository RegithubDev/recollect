package com.resustainability.recollect.service;

import com.resustainability.recollect.repository.ScrapRegionAvailabilityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ScrapRegionAvailabilityService {
    private final ScrapRegionAvailabilityRepository scrapRegionAvailabilityRepository;

    @Autowired
    public ScrapRegionAvailabilityService(ScrapRegionAvailabilityRepository scrapRegionAvailabilityRepository) {
        this.scrapRegionAvailabilityRepository = scrapRegionAvailabilityRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean bookSlot(Long availabilityId) {
        return 1 == scrapRegionAvailabilityRepository.decrementRemainingSlot(availabilityId);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean bookSlot(Long scrapRegionId, LocalDate scheduleDate) {
        return 1 == scrapRegionAvailabilityRepository.decrementRemainingSlot(scrapRegionId, scheduleDate);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean freeSlot(Long scrapRegionId, LocalDate scheduleDate) {
        return 1 == scrapRegionAvailabilityRepository.incrementRemainingSlot(scrapRegionId, scheduleDate);
    }
}
