package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.BioWasteOrders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BioWasteOrdersRepository extends JpaRepository<BioWasteOrders, Long> {
    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE BioWasteOrders bo
        SET bo.reason.id = :orderCancelReasonId,
            bo.orderStatus = :orderStatus
        WHERE bo.id = :id
    """)
    int cancelByBioWasteOrderId(
            @Param("id") Long bioWasteOrderId,
            @Param("orderCancelReasonId") Long orderCancelReasonId,
            @Param("orderStatus") String orderStatus
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE BioWasteOrders bo
        SET bo.isDeleted = :isDeleted
        WHERE bo.id = :id
    """)
    int deleteByBioWasteOrderId(
            @Param("id") Long bioWasteOrderId,
            @Param("isDeleted") boolean isDeleted
    );
}
