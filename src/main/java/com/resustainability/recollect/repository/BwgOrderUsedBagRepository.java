package com.resustainability.recollect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.resustainability.recollect.dto.response.IBwgOrderUsedBagResponse;
import com.resustainability.recollect.entity.backend.BwgOrderUsedBag;

import java.util.List;

@Repository
public interface BwgOrderUsedBagRepository extends JpaRepository<BwgOrderUsedBag, Long> {
    @Query("""
        SELECT
            ub.id AS id,
            ub.numberOfBags AS numberOfBags,
            ub.totalBagPrice AS totalBagPrice,
            ub.finalPrice AS finalPrice,
            ub.cgstPrice AS cgstPrice,
            ub.sgstPrice AS sgstPrice,
            b.id AS bagId,
            b.bagSize AS bagSize,
            o.id AS orderId
        FROM BwgOrderUsedBag ub
        JOIN ub.bag b
        JOIN ub.order o
        WHERE ub.isDeleted = false
        AND o.id = :orderId
    """)
    List<IBwgOrderUsedBagResponse> findAllUsedBagsByOrderId(
            @Param("orderId") Long orderId
    );

    @Query("""
        SELECT 
            ub.id AS id,
            ub.numberOfBags AS numberOfBags,
            ub.totalBagPrice AS totalBagPrice,
            ub.finalPrice AS finalPrice,
            ub.cgstPrice AS cgstPrice,
            ub.sgstPrice AS sgstPrice,
            b.id AS bagId,
            b.bagSize AS bagSize,
            o.id AS orderId
        FROM BwgOrderUsedBag ub
        JOIN ub.bag b
        JOIN ub.order o
        WHERE ub.isDeleted = false
        AND o.id = :orderId
    """)
    Page<IBwgOrderUsedBagResponse> findAllByOrderId(
            @Param("orderId") Long orderId,
            Pageable pageable
    );

    @Modifying
    @Query("""
        UPDATE BwgOrderUsedBag ub
        SET ub.isDeleted = :isDeleted
        WHERE ub.id = :id
    """)
    int softDelete(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);
}
