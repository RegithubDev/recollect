package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IOrderLogResponse;
import com.resustainability.recollect.entity.backend.CompleteOrderLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteOrderLogRepository extends JpaRepository<CompleteOrderLog, Long> {
    @Query("""
        SELECT
            ol.id AS id,
            ol.admin.id AS adminId,
            ol.client.id AS clientId,
            o.id AS orderId,
            ol.provider.id AS providerId,
            ol.customer.id AS customerId,
            ol.doneBy AS doneBy,
            ol.description AS description,
            ol.createdAt AS createdAt
        FROM CompleteOrderLog ol
        JOIN ol.order o
        WHERE o.id = :orderId
            AND (
              :searchTerm IS NULL OR :searchTerm = '' OR
              ol.doneBy LIKE CONCAT('%', :searchTerm, '%') OR
              ol.description LIKE CONCAT('%', :searchTerm, '%')
          )
    """)
    Page<IOrderLogResponse> findLogsByCompleteOrderId(
            @Param("orderId") Long completeOrderId,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
}
