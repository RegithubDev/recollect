package com.resustainability.recollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.resustainability.recollect.dto.response.IBwgOrderResponse;
import com.resustainability.recollect.entity.backend.BwgOrders;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface BwgOrdersRepository extends JpaRepository<BwgOrders, Long> {

	@Query("""
		    SELECT
		        o.id AS id,
		        o.orderCode AS orderCode,
		        o.orderDate AS orderDate,
		        o.scheduleDate AS scheduleDate,
		        o.orderStatus AS orderStatus,
		        o.dueSettled AS dueSettled,
				o.orderType AS orderType,

		        c.id AS clientId,
		        c.fullName AS clientName,
		        c.phoneNumber AS phoneNumber,  

		        s.id AS stateId,
		        s.stateName AS stateName,

		        d.id AS districtId,
		        d.districtName AS districtName,

		        sr.id AS scrapRegionId,
		        sr.regionName AS scrapRegionName
		    FROM BwgOrders o
		    JOIN o.client c
		    LEFT JOIN o.state s
		    LEFT JOIN c.district d
		    LEFT JOIN c.scrapRegion sr
		    WHERE o.isDeleted = false
		      AND (:orderType IS NULL OR o.orderType = :orderType)
		""")
		Page<IBwgOrderResponse> findAllPaged(
		        @Param("orderType") String orderType,
		        Pageable pageable
		);




	@Query("""
		    SELECT
		        o.id AS id,
		        o.orderCode AS orderCode,
		        o.orderDate AS orderDate,
		        o.scheduleDate AS scheduleDate,
		        o.orderStatus AS orderStatus,
		        o.dueSettled AS dueSettled,
		        o.preferredPaymentMethod AS preferredPaymentMethod,
				o.orderType AS orderType,
				o.comment AS comment,

		        c.id AS clientId,
		        c.fullName AS clientName,
		        c.phoneNumber AS phoneNumber,

		        s.id AS stateId,
		        s.stateName AS stateName,

		        d.id AS districtId,
		        d.districtName AS districtName,

		        sr.id AS scrapRegionId,
		        sr.regionName AS scrapRegionName
		    FROM BwgOrders o
		    JOIN o.client c
		    LEFT JOIN o.state s
		    LEFT JOIN c.district d
		    LEFT JOIN c.scrapRegion sr
		    WHERE o.id = :id
		""")
		Optional<IBwgOrderResponse> findOrderById(@Param("id") Long id);

	@Modifying
	@Query("""
        UPDATE BwgOrders o
        SET o.scheduleDate = :scheduledDate
        WHERE o.id = :id
    """)
	int updateScheduledDate(
			@Param("id") Long id,
			@Param("scheduledDate") LocalDate scheduledDate
	);
	
	

    @Modifying
    @Query("""
        UPDATE BwgOrders o
        SET o.isDeleted = :isDeleted
        WHERE o.id = :id
    """)
    int softDelete(@Param("id") Long id, @Param("isDeleted") boolean isDeleted);
}
