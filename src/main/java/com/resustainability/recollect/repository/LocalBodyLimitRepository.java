package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.ILocalBodyLimitResponse;
import com.resustainability.recollect.entity.backend.LocalBodyLimit;

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
public interface LocalBodyLimitRepository extends JpaRepository<LocalBodyLimit, Long> {
    @Query("""
        SELECT
            lbl.id AS id,
            lbl.localbody.id AS localBodyId,
            lbl.availableDate AS date,
            lbl.limit AS limit,
            lbl.remainingSlots AS remainingSlots
        FROM LocalBodyLimit lbl
        WHERE lbl.localbody.id = :localBodyId
          AND lbl.availableDate BETWEEN :fromDate AND :toDate
          AND (:onlyWithRemainingSlots = false OR lbl.remainingSlots > 0)
    """)
    List<ILocalBodyLimitResponse> findAllByLocalBodyIdAndBetween(
            @Param("localBodyId") Long localBodyId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("onlyWithRemainingSlots") boolean onlyWithRemainingSlots
    );

    @Query(nativeQuery = true, value = """
        SELECT
            lbl.id AS id,
            lb.id AS localBodyId,
            lbl.available_date AS date,
            lbl.`limit` AS `limit`,
            lbl.remaining_slots AS remainingSlots
        FROM backend_localbodylimit lbl
        JOIN backend_localbody lb
            ON lb.id = lbl.localbody_id
        JOIN (
            SELECT
                localbody_id,
                MAX(available_date) AS last_date
            FROM backend_localbodylimit
            GROUP BY localbody_id
        ) last_entries
            ON last_entries.localbody_id = lbl.localbody_id
        WHERE lbl.available_date BETWEEN
              DATE_SUB(last_entries.last_date, INTERVAL :months MONTH)
              AND last_entries.last_date;
    """)
    List<ILocalBodyLimitResponse> findAllWhereLastEntryOlderThanXMonths(
            @Param("months") int months
    );

    @Query("""
        SELECT lbl
        FROM LocalBodyLimit lbl
        WHERE lbl.localbody.id = :localBodyId
          AND lbl.availableDate BETWEEN :fromDate AND :toDate
    """)
    List<LocalBodyLimit> findBetween(
            @Param("localBodyId") Long localBodyId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    @Query("""
        SELECT MAX(lbl.availableDate)
        FROM LocalBodyLimit lbl
        WHERE lbl.localbody.id = :localBodyId
    """)
    Optional<LocalDate> findMaxDate(
            @Param("localBodyId") Long localBodyId
    );

    @Query("""
        SELECT lbl
        FROM LocalBodyLimit lbl
        WHERE lbl.localbody.id = :localBodyId
          AND lbl.availableDate IN :availableDates
    """)
    List<LocalBodyLimit> findAllByLocalBodyIdAndDates(
            @Param("localBodyId") Long localBodyId,
            @Param("availableDates") Set<LocalDate> availableDates
    );

    @Query("""
        SELECT lbl.availableDate
        FROM LocalBodyLimit lbl
        WHERE lbl.localbody.id = :localBodyId
          AND lbl.availableDate IN :dates
    """)
    Set<LocalDate> findExistingDates(
            @Param("localBodyId") Long localBodyId,
            @Param("dates") Set<LocalDate> dates
    );

    @Modifying
    @Query("""
        UPDATE LocalBodyLimit lbl
        SET lbl.remainingSlots = lbl.remainingSlots - 1
        WHERE lbl.id = :id AND lbl.remainingSlots > 0
    """)
    int decrementRemainingSlot(@Param("id") Long id);

    @Modifying
    @Query("""
        UPDATE LocalBodyLimit lbl
        SET lbl.remainingSlots = lbl.remainingSlots - 1
        WHERE lbl.localbody.id = :localBodyId
          AND lbl.availableDate = :scheduleDate
          AND lbl.remainingSlots > 0
    """)
    int decrementRemainingSlot(
            @Param("localBodyId") Long localBodyId,
            @Param("scheduleDate") LocalDate scheduleDate
    );

    @Modifying
    @Query("""
        UPDATE LocalBodyLimit lbl
        SET lbl.remainingSlots = lbl.remainingSlots + 1
        WHERE lbl.localbody.id = :localBodyId
          AND lbl.availableDate = :scheduleDate
          AND lbl.remainingSlots < lbl.limit
    """)
    int incrementRemainingSlot(
            @Param("localBodyId") Long localBodyId,
            @Param("scheduleDate") LocalDate scheduleDate
    );
}
