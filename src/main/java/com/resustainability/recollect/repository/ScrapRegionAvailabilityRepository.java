package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IScrapRegionAvailabilityResponse;
import com.resustainability.recollect.entity.backend.ScrapRegionAvailability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ScrapRegionAvailabilityRepository extends JpaRepository<ScrapRegionAvailability, Long> {
    @Query("""
        SELECT
            sra.id AS id,
            sra.availableDate AS availableDate,
            sra.limit AS limit,
            sra.remainingSlots AS remainingSlots
        FROM ScrapRegionAvailability sra
        WHERE sra.scrapRegion.id = :scrapRegionId
          AND sra.availableDate BETWEEN :fromDate AND :toDate
          AND (:onlyWithRemainingSlots = false OR sra.remainingSlots > 0)
    """)
    List<IScrapRegionAvailabilityResponse> findAllByScrapRegionIdAndBetween(
            @Param("scrapRegionId") Long scrapRegionId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("onlyWithRemainingSlots") boolean onlyWithRemainingSlots
    );

    @Query("""
        SELECT sra
        FROM ScrapRegionAvailability sra
        WHERE sra.scrapRegion.id = :scrapRegionId
          AND sra.availableDate IN :availableDates
    """)
    List<ScrapRegionAvailability> findAllByScrapRegionIdAndDates(
            @Param("scrapRegionId") Long scrapRegionId,
            @Param("availableDates") Set<LocalDate> availableDates
    );

    @Modifying
    @Query("""
        UPDATE ScrapRegionAvailability sra
        SET sra.remainingSlots = sra.remainingSlots - 1
        WHERE sra.id = :id AND sra.remainingSlots > 0
    """)
    int decrementRemainingSlot(@Param("id") Long id);

    @Modifying
    @Query("""
        UPDATE ScrapRegionAvailability sra
        SET sra.remainingSlots = sra.remainingSlots - 1
        WHERE sra.scrapRegion.id = :scrapRegionId
          AND sra.availableDate = :scheduleDate
          AND sra.remainingSlots > 0
    """)
    int decrementRemainingSlot(
            @Param("scrapRegionId") Long scrapRegionId,
            @Param("scheduleDate") LocalDate scheduleDate
    );

    @Modifying
    @Query("""
        UPDATE ScrapRegionAvailability sra
        SET sra.remainingSlots = sra.remainingSlots + 1
        WHERE sra.scrapRegion.id = :scrapRegionId
          AND sra.availableDate = :scheduleDate
          AND sra.remainingSlots < sra.limit
    """)
    int incrementRemainingSlot(
            @Param("scrapRegionId") Long scrapRegionId,
            @Param("scheduleDate") LocalDate scheduleDate
    );
}
