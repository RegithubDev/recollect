package com.resustainability.recollect.service;

import com.resustainability.recollect.dto.response.ILocalBodyLimitResponse;
import com.resustainability.recollect.entity.backend.LocalBody;
import com.resustainability.recollect.entity.backend.LocalBodyLimit;
import com.resustainability.recollect.repository.LocalBodyLimitRepository;
import com.resustainability.recollect.repository.LocalBodyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LocalBodyAvailabilityService {
    private final LocalBodyRepository localBodyRepository;
    private final LocalBodyLimitRepository localBodyLimitRepository;

    @Autowired
    public LocalBodyAvailabilityService(
            LocalBodyRepository localBodyRepository,
            LocalBodyLimitRepository localBodyLimitRepository
    ) {
        this.localBodyRepository = localBodyRepository;
        this.localBodyLimitRepository = localBodyLimitRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean bookSlot(Long availabilityId) {
        return 1 == localBodyLimitRepository.decrementRemainingSlot(availabilityId);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean bookSlot(Long scrapRegionId, LocalDate scheduleDate) {
        return 1 == localBodyLimitRepository.decrementRemainingSlot(scrapRegionId, scheduleDate);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean freeSlot(Long scrapRegionId, LocalDate scheduleDate) {
        return 1 == localBodyLimitRepository.incrementRemainingSlot(scrapRegionId, scheduleDate);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void extendAvailability(final int horizonMonths) {
        final List<LocalBodyLimit> toSave = new ArrayList<>();

        final Map<Long, List<ILocalBodyLimitResponse>> grouped = localBodyLimitRepository
                .findAllWhereLastEntryOlderThanXMonths(horizonMonths)
                .stream()
                .collect(Collectors.groupingBy(
                        ILocalBodyLimitResponse::getLocalBodyId
                ));

        for (Map.Entry<Long, List<ILocalBodyLimitResponse>> entry : grouped.entrySet()) {
            final Long localBodyId = entry.getKey();
            final LocalBody localBody = localBodyRepository.getReferenceById(localBodyId);

            final Map<LocalDate, Integer> indexedLimits = new HashMap<>();
            final Set<LocalDate> futureDates = new HashSet<>();

            for (final ILocalBodyLimitResponse lba : entry.getValue()) {
                for (int i = 1; i <= horizonMonths; i++) {
                    final LocalDate date = lba.getDate().plusMonths(i);
                    futureDates.add(date);
                    indexedLimits.putIfAbsent(date, lba.getLimit());
                }
            }

            if (futureDates.isEmpty()) continue;

            final Set<LocalDate> existingDates = localBodyLimitRepository
                    .findExistingDates(localBodyId, futureDates);

            futureDates.removeAll(existingDates);

            if (futureDates.isEmpty()) continue;

            for (final LocalDate date : futureDates) {
                final Integer limit = indexedLimits.get(date);
                toSave.add(new LocalBodyLimit(
                        null,
                        date,
                        limit,
                        limit,
                        localBody
                ));
            }
        }

        if (!toSave.isEmpty()) {
            localBodyLimitRepository.saveAll(toSave);
        }
    }
}
