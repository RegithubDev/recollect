package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IOrderCancelReasonResponse;
import com.resustainability.recollect.entity.backend.OrderCancelReason;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCancelReasonRepository extends JpaRepository<OrderCancelReason, Long> {
    @Query("""
        SELECT
            ocr.id AS id,
            ocr.reason AS reason,
            ocr.orderType AS orderType,
            ocr.isActive AS isActive
        FROM OrderCancelReason ocr
        WHERE ocr.isActive = true
    """)
    List<IOrderCancelReasonResponse> findAllOrderCancelReasons();

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE OrderCancelReason ocr
        SET ocr.isActive = :isActive
        WHERE ocr.id = :id
    """)
    int toggleById(
            @Param("id") Long orderCancelReasonId,
            @Param("isActive") boolean isActive
    );
}
