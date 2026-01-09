package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.ScrapOrders;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapOrdersRepository extends JpaRepository<ScrapOrders, Long> {
    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE ScrapOrders so
        SET so.reason.id = :orderCancelReasonId,
            so.orderStatus = :orderStatus
        WHERE so.id = :id
    """)
    int cancelByScrapOrderId(
            @Param("id") Long scrapOrderId,
            @Param("orderCancelReasonId") Long orderCancelReasonId,
            @Param("orderStatus") String orderStatus
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE ScrapOrders so
        SET so.orderStatus = :orderStatus
        WHERE so.id = :id
    """)
    int updateStatusByScrapOrderId(
            @Param("id") Long scrapOrderId,
            @Param("orderStatus") String orderStatus
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE ScrapOrders so
        SET so.isDeleted = :isDeleted
        WHERE so.id = :id
    """)
    int deleteByScrapOrderId(
            @Param("id") Long scrapOrderId,
            @Param("isDeleted") boolean isDeleted
    );
    
    @Modifying
    @Query("""
        update ScrapOrders so
        set so.scheduleDate = :scheduleDate
        where so.id = :id
    """)
    int updateScheduleDate(
            @Param("id") Long id,
            @Param("scheduleDate") LocalDate scheduleDate
    );

}
