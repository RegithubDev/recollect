package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.dto.response.IScrapRegionAvailabilityResponse;
import com.resustainability.recollect.entity.backend.ScrapRegion;
import com.resustainability.recollect.entity.backend.ScrapRegionAvailability;
import com.resustainability.recollect.repository.ScrapRegionAvailabilityRepository;
import com.resustainability.recollect.repository.ScrapRegionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ScrapRegionAvailabilityService {
    private final ScrapRegionRepository scrapRegionRepository;
    private final ScrapRegionAvailabilityRepository scrapRegionAvailabilityRepository;

    @Autowired
    public ScrapRegionAvailabilityService(
            ScrapRegionRepository scrapRegionRepository,
            ScrapRegionAvailabilityRepository scrapRegionAvailabilityRepository
    ) {
        this.scrapRegionRepository = scrapRegionRepository;
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


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void extendAvailability(final int horizonMonths) {
        final List<ScrapRegionAvailability> toSave = new ArrayList<>();

        final Map<Long, List<IScrapRegionAvailabilityResponse>> grouped = scrapRegionAvailabilityRepository
                .findAllWhereLastEntryOlderThanXMonths(horizonMonths)
                .stream()
                .collect(Collectors.groupingBy(
                        IScrapRegionAvailabilityResponse::getScrapRegionId
                ));

        for (Map.Entry<Long, List<IScrapRegionAvailabilityResponse>> entry : grouped.entrySet()) {
            final Long regionId = entry.getKey();
            final ScrapRegion region = scrapRegionRepository.getReferenceById(regionId);

            final Map<LocalDate, Integer> indexedLimits = new HashMap<>();
            final Set<LocalDate> futureDates = new HashSet<>();

            for (final IScrapRegionAvailabilityResponse sra : entry.getValue()) {
                for (int i = 1; i <= horizonMonths; i++) {
                    final LocalDate date = sra.getDate().plusMonths(i);
                    futureDates.add(date);
                    indexedLimits.putIfAbsent(date, sra.getLimit());
                }
            }

            if (futureDates.isEmpty()) continue;

            final Set<LocalDate> existingDates = scrapRegionAvailabilityRepository
                    .findExistingDates(regionId, futureDates);

            futureDates.removeAll(existingDates);

            if (futureDates.isEmpty()) continue;

            for (final LocalDate date : futureDates) {
                final Integer limit = indexedLimits.get(date);
                toSave.add(new ScrapRegionAvailability(
                        null,
                        date,
                        limit,
                        limit,
                        region
                ));
            }
        }

        if (!toSave.isEmpty()) {
            scrapRegionAvailabilityRepository.saveAll(toSave);
        }
    }
}
