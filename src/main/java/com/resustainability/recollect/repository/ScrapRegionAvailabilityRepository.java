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
import java.util.Optional;
import java.util.Set;

@Repository
public interface ScrapRegionAvailabilityRepository extends JpaRepository<ScrapRegionAvailability, Long> {
    @Query("""
        SELECT
            sra.id AS id,
            sra.scrapRegion.id AS scrapRegionId,
            sra.availableDate AS date,
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

    @Query(nativeQuery = true, value = """
        SELECT
            sra.id AS id,
            sr.id AS scrapRegionId,
            sra.available_date AS date,
            sra.`limit` AS `limit`,
            sra.remaining_slots AS remainingSlots
        FROM backend_scrapregionavailability sra
        JOIN backend_scrapregion sr
            ON sr.id = sra.scrap_region_id
        JOIN (
            SELECT
                scrap_region_id,
                MAX(available_date) AS last_date
            FROM backend_scrapregionavailability
            GROUP BY scrap_region_id
        ) last_entries
            ON last_entries.scrap_region_id = sra.scrap_region_id
        WHERE sra.available_date BETWEEN
              DATE_SUB(last_entries.last_date, INTERVAL :months MONTH)
              AND last_entries.last_date;
    """)
    List<IScrapRegionAvailabilityResponse> findAllWhereLastEntryOlderThanXMonths(
            @Param("months") int months
    );

    @Query("""
        SELECT sra
        FROM ScrapRegionAvailability sra
        WHERE sra.scrapRegion.id = :scrapRegionId
          AND sra.availableDate BETWEEN :fromDate AND :toDate
    """)
    List<ScrapRegionAvailability> findBetween(
            @Param("scrapRegionId") Long scrapRegionId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    @Query("""
        SELECT MAX(sra.availableDate)
        FROM ScrapRegionAvailability sra
        WHERE sra.scrapRegion.id = :scrapRegionId
    """)
    Optional<LocalDate> findMaxDate(
            @Param("scrapRegionId") Long scrapRegionId
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

    @Query("""
        SELECT sra.availableDate
        FROM ScrapRegionAvailability sra
        WHERE sra.scrapRegion.id = :scrapRegionId
          AND sra.availableDate IN :dates
    """)
    Set<LocalDate> findExistingDates(
            @Param("scrapRegionId") Long scrapRegionId,
            @Param("dates") Set<LocalDate> dates
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
