package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IOrderCartItemResponse;
import com.resustainability.recollect.dto.response.IOrderHistoryResponse;
import com.resustainability.recollect.dto.response.InvoiceResponse;
import com.resustainability.recollect.entity.backend.CompleteOrders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface CompleteOrdersRepository extends JpaRepository<CompleteOrders, Long> {
	@Query("""
		SELECT
			o.id AS id,
			c.id AS customerId,
			c.fullName AS fullName,
			c.phoneNumber AS customerPhoneNumber,
			d.id AS districtId,
			d.districtName AS districtName,
			d.districtCode AS districtCode, 
			sr.id AS scrapRegionId,
			sr.regionName AS scrapRegionName, 
			w.id AS wardId,
		    w.wardNo AS wardNo,
		    w.wardName AS wardName,
		    lb.id AS localBodyId,
		    lb.localBodyName AS localBodyName,       
			COALESCE(so.orderCode, bo.orderCode) AS code,
			o.orderType AS type,
			o.scheduleDate AS scheduleDate,
			COALESCE(so.orderDate, bo.orderDate) AS orderDate,
			o.orderStatus AS status,
			pv.vehicleNumber AS vehicle,
			so.platform,
			p.fullName AS providerName,
			p.id AS providerId,
			p.phoneNumber AS providerPhoneNumber,
			so.billType AS scrapBillType,
			bo.billType AS dhbillType
		FROM CompleteOrders o
		JOIN o.customer c
		LEFT JOIN c.ward w
		LEFT JOIN w.localbody lb
		LEFT JOIN c.scrapRegion sr
		LEFT JOIN c.district d
		LEFT JOIN o.scrapOrder so
		LEFT JOIN o.bioWasteOrder bo
		LEFT JOIN o.vehicle pv
		LEFT JOIN o.provider p
		WHERE o.isDeleted = false
		  AND (so.id IS NOT NULL OR bo.id IS NOT NULL)
		  AND (:orderType IS NULL OR o.orderType = :orderType)
		  AND (:orderStatuses IS NULL OR o.orderStatus IN :orderStatuses)
		  AND (
			  :searchTerm IS NULL OR :searchTerm = '' OR
			  c.fullName LIKE CONCAT(:searchTerm, '%') OR
			  COALESCE(so.orderCode, bo.orderCode) LIKE CONCAT(:searchTerm, '%') OR
			  o.orderStatus LIKE CONCAT(:searchTerm, '%')
		  )
	""")
	Page<IOrderHistoryResponse> findAllPaged(
			@Param("orderStatuses") Set<String> orderStatuses,
			@Param("orderType") String  orderType,
			@Param("searchTerm") String searchTerm,
			Pageable pageable
	);
    
	

    @Query("""
		SELECT
			o.id AS id,
			c.id AS customerId,
			c.fullName AS fullName,
			c.phoneNumber AS customerPhoneNumber,

			COALESCE(so.orderCode, bo.orderCode) AS code,
			o.orderType AS type,
			o.scheduleDate AS scheduleDate,
			COALESCE(so.orderDate, bo.orderDate) AS orderDate,
			o.orderStatus AS status

		FROM CompleteOrders o
		JOIN o.customer c
		LEFT JOIN o.scrapOrder so
		LEFT JOIN o.bioWasteOrder bo

		WHERE o.isDeleted = false
		  AND c.id = :customerId
		  AND (so.id IS NOT NULL OR bo.id IS NOT NULL)
		  AND (:orderType IS NULL OR o.orderType = :orderType)
		  AND (:orderStatuses IS NULL OR o.orderStatus IN :orderStatuses)
		  AND (
			  :searchTerm IS NULL OR :searchTerm = '' OR
			  c.fullName LIKE CONCAT(:searchTerm, '%') OR
			  COALESCE(so.orderCode, bo.orderCode) LIKE CONCAT(:searchTerm, '%') OR
			  o.orderStatus LIKE CONCAT(:searchTerm, '%')
		  )
	""")
	Page<IOrderHistoryResponse> findAllPagedIfBelongsToCustomer(
			@Param("customerId") Long customerId,
			@Param("orderStatuses") Set<String> orderStatuses,
			@Param("orderType") String orderType,
			@Param("searchTerm") String searchTerm,
			Pageable pageable
	);

    @Query("""
		SELECT
			o.id AS id,
			so.id AS scrapOrderId,
			sr.id AS scrapRegionId,
			sr.regionName AS scrapRegionName,
			w.id AS wardId,
			w.wardNo AS wardNo,
			w.wardName AS wardName,
			lb.id AS localBodyId,
			lb.localBodyName AS localBodyName,
			d.id AS districtId,
			d.districtName AS districtName,
			d.districtCode AS districtCode,
			p.id AS providerId,
			p.fullName AS providerName,
			bo.id AS bioWasteOrderId,
			c.id AS customerId,
			c.fullName AS fullName,
			COALESCE(so.orderCode, bo.orderCode) AS code,
			o.orderType AS type,
			o.scheduleDate AS scheduleDate,
			COALESCE(so.orderDate, bo.orderDate) AS orderDate,
			o.orderStatus AS status,
			COALESCE(aso.id, abo.id) AS addressId,
			COALESCE(aso.residenceType, abo.residenceType) AS residenceType,
			COALESCE(aso.residenceDetails, abo.residenceDetails) AS residenceDetails,
			COALESCE(aso.landmark, abo.landmark) AS landmark,
			COALESCE(aso.latitude, abo.latitude) AS latitude,
			COALESCE(aso.longitude, abo.longitude) AS longitude,
			COALESCE(aso.isDeleted, abo.isDeleted) AS isAddressDeleted
		FROM CompleteOrders o
		JOIN o.customer c
		JOIN o.district d
		JOIN o.provider p
		LEFT JOIN o.scrapOrder so
		LEFT JOIN so.address aso
		LEFT JOIN so.scrapRegion sr
		LEFT JOIN o.bioWasteOrder bo
		LEFT JOIN bo.address abo
		LEFT JOIN abo.ward w
		LEFT JOIN w.localbody lb
		WHERE o.isDeleted = false
		  AND p.id = :providerId
		  AND (so.id IS NOT NULL OR bo.id IS NOT NULL)
		  AND (:orderType IS NULL OR o.orderType = :orderType)
		  AND (:orderStatuses IS NULL OR o.orderStatus IN :orderStatuses)
		  AND (
			  :searchTerm IS NULL OR :searchTerm = '' OR
			  sr.regionName LIKE CONCAT(:searchTerm, '%') OR
			  c.fullName LIKE CONCAT(:searchTerm, '%') OR
			  COALESCE(so.orderCode, bo.orderCode) LIKE CONCAT(:searchTerm, '%') OR
			  o.orderType LIKE CONCAT(:searchTerm, '%') OR
			  o.orderStatus LIKE CONCAT(:searchTerm, '%')
		  )
	""")
	Page<IOrderHistoryResponse> findAllPagedIfBelongsToProvider(
			@Param("providerId") Long providerId,
			@Param("orderStatuses") Set<String> orderStatuses,
			@Param("orderType") String orderType,
			@Param("searchTerm") String searchTerm,
			Pageable pageable
	);

    @Query("""
        SELECT
            o.id AS id,
            so.id AS scrapOrderId,
            sr.id AS scrapRegionId,
            sr.regionName AS scrapRegionName,
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName,
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
            p.id AS providerId,
            p.fullName AS providerName,
            bo.id AS bioWasteOrderId,
            c.id AS customerId,
            c.fullName AS fullName,
            COALESCE(so.orderCode, bo.orderCode) AS code,
            o.orderType AS type,
            o.scheduleDate AS scheduleDate,
            COALESCE(so.orderDate, bo.orderDate) AS orderDate,
            o.orderStatus AS status,
            COALESCE(aso.id, abo.id) AS addressId,
            COALESCE(aso.residenceType, abo.residenceType) AS residenceType,
            COALESCE(aso.residenceDetails, abo.residenceDetails) AS residenceDetails,
            COALESCE(aso.landmark, abo.landmark) AS landmark,
            COALESCE(aso.latitude, abo.latitude) AS latitude,
            COALESCE(aso.longitude, abo.longitude) AS longitude,
            COALESCE(aso.isDeleted, abo.isDeleted) AS isAddressDeleted
        FROM CompleteOrders o
        JOIN o.customer c
        JOIN o.district d
        LEFT JOIN o.provider p
        LEFT JOIN o.scrapOrder so
        LEFT JOIN so.address aso
        LEFT JOIN so.scrapRegion sr
        LEFT JOIN o.bioWasteOrder bo
        LEFT JOIN bo.address abo
        LEFT JOIN abo.ward w
        LEFT JOIN w.localbody lb
        WHERE o.isDeleted = false
            AND p IS NULL
            AND o.orderStatus = :orderStatus
            AND (so.id IS NOT NULL OR bo.id IS NOT NULL)
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                COALESCE(so.orderCode, bo.orderCode) LIKE CONCAT(:searchTerm, '%') OR
                o.orderType LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IOrderHistoryResponse> findAllAssignablePaged(
            @Param("orderStatus") String orderStatus,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            o.id AS id,
            so.id AS scrapOrderId,
            sr.id AS scrapRegionId,
            sr.regionName AS scrapRegionName,
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName,
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
            p.id AS providerId,
            p.fullName AS providerName,
            bo.id AS bioWasteOrderId,
            c.id AS customerId,
            c.fullName AS fullName,
            COALESCE(so.orderCode, bo.orderCode) AS code,
            o.orderType AS type,
            o.scheduleDate AS scheduleDate,
            COALESCE(so.orderDate, bo.orderDate) AS orderDate,
            o.orderStatus AS status,
            COALESCE(aso.id, abo.id) AS addressId,
            COALESCE(aso.residenceType, abo.residenceType) AS residenceType,
            COALESCE(aso.residenceDetails, abo.residenceDetails) AS residenceDetails,
            COALESCE(aso.landmark, abo.landmark) AS landmark,
            COALESCE(aso.latitude, abo.latitude) AS latitude,
            COALESCE(aso.longitude, abo.longitude) AS longitude,
            COALESCE(aso.isDeleted, abo.isDeleted) AS isAddressDeleted
        FROM CompleteOrders o
        JOIN o.customer c
        JOIN o.district d
        LEFT JOIN o.provider p
        LEFT JOIN o.scrapOrder so
        LEFT JOIN so.address aso
        LEFT JOIN so.scrapRegion sr
        LEFT JOIN o.bioWasteOrder bo
        LEFT JOIN bo.address abo
        LEFT JOIN abo.ward w
        LEFT JOIN w.localbody lb
        WHERE o.isDeleted = false
            AND p IS NULL
            AND o.orderStatus = :orderStatus
            AND (so.id IS NOT NULL OR bo.id IS NOT NULL)
            AND d.id IN :districtIds
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                COALESCE(so.orderCode, bo.orderCode) LIKE CONCAT(:searchTerm, '%') OR
                o.orderType LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IOrderHistoryResponse> findAllAssignablePagedIfBelongs(
            @Param("orderStatus") String orderStatus,
            @Param("districtIds") Set<Long> districtIds,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            o.id AS id,
            so.id AS scrapOrderId,
            sr.id AS scrapRegionId,
            sr.regionName AS scrapRegionName,
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName,
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
            p.id AS providerId,
            p.fullName AS providerName,
            bo.id AS bioWasteOrderId,
            c.id AS customerId,
            c.fullName AS fullName,
            c.phoneNumber AS customerPhoneNumber,
            COALESCE(so.orderCode, bo.orderCode) AS code,
            o.orderType AS type,
            o.scheduleDate AS scheduleDate,
            COALESCE(so.orderDate, bo.orderDate) AS orderDate,
            o.orderStatus AS status,
            COALESCE(aso.id, abo.id) AS addressId,
            COALESCE(aso.residenceType, abo.residenceType) AS residenceType,
            COALESCE(aso.residenceDetails, abo.residenceDetails) AS residenceDetails,
            COALESCE(aso.landmark, abo.landmark) AS landmark,
            COALESCE(aso.latitude, abo.latitude) AS latitude,
            COALESCE(aso.longitude, abo.longitude) AS longitude,
            COALESCE(aso.isDeleted, abo.isDeleted) AS isAddressDeleted
        FROM CompleteOrders o
        JOIN o.customer c
        JOIN o.district d
        LEFT JOIN o.provider p
        LEFT JOIN o.scrapOrder so
        LEFT JOIN so.address aso
        LEFT JOIN so.scrapRegion sr
        LEFT JOIN o.bioWasteOrder bo
        LEFT JOIN bo.address abo
        LEFT JOIN abo.ward w
        LEFT JOIN w.localbody lb
        WHERE (so.id IS NOT NULL OR bo.id IS NOT NULL)
        AND o.id = :id
    """)
    Optional<IOrderHistoryResponse> findByCompleteOrderId(
            @Param("id") Long completeOrderId
    );

    @Query("""
        SELECT
            o.id AS id,
            so.id AS scrapOrderId,
            sr.id AS scrapRegionId,
            sr.regionName AS scrapRegionName,
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName,
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
            p.id AS providerId,
            p.fullName AS providerName,
            bo.id AS bioWasteOrderId,
            c.id AS customerId,
            c.fullName AS fullName,
            c.phoneNumber AS customerPhoneNumber,
            COALESCE(so.orderCode, bo.orderCode) AS code,
            o.orderType AS type,
            o.scheduleDate AS scheduleDate,
            COALESCE(so.orderDate, bo.orderDate) AS orderDate,
            o.orderStatus AS status,
            COALESCE(aso.id, abo.id) AS addressId,
            COALESCE(aso.residenceType, abo.residenceType) AS residenceType,
            COALESCE(aso.residenceDetails, abo.residenceDetails) AS residenceDetails,
            COALESCE(aso.landmark, abo.landmark) AS landmark,
            COALESCE(aso.latitude, abo.latitude) AS latitude,
            COALESCE(aso.longitude, abo.longitude) AS longitude,
            COALESCE(aso.isDeleted, abo.isDeleted) AS isAddressDeleted
        FROM CompleteOrders o
        JOIN o.customer c
        JOIN o.district d
        LEFT JOIN o.provider p
        LEFT JOIN o.scrapOrder so
        LEFT JOIN so.address aso
        LEFT JOIN so.scrapRegion sr
        LEFT JOIN o.bioWasteOrder bo
        LEFT JOIN bo.address abo
        LEFT JOIN abo.ward w
        LEFT JOIN w.localbody lb
        WHERE (so.id IS NOT NULL OR bo.id IS NOT NULL)
        AND o.id = :id
        AND c.id = :customerId
    """)
    Optional<IOrderHistoryResponse> findByCompleteOrderIdIfBelongs(
            @Param("customerId") Long customerId,
            @Param("id") Long completeOrderId
    );

    @Query("""
        SELECT
            o.id AS id,
            o.orderType AS type,
            o.bioWeight AS weight,
            o.clientPrice AS clientPrice,
            o.bioBillAmount AS subTotal,
            o.bioCgstAmount AS cgstAmount,
            o.bioSgstAmount AS sgstAmount,
            o.bioBagAmount AS bagAmount,
            COALESCE(o.bioTotalBill, o.scrapTotalBill) AS totalBill
        FROM CompleteOrders o
        JOIN o.bwgOrder bwg
        WHERE bwg.id = :id
    """)
    Optional<InvoiceResponse> findInvoiceDetailsByBwgOrderId(
            @Param("id") Long bwgOrderId
    );

    @Query("""
        SELECT
            o.id AS id,
            o.orderType AS type,
            o.bioWeight AS weight,
            o.clientPrice AS clientPrice,
            o.bioBillAmount AS subTotal,
            o.bioCgstAmount AS cgstAmount,
            o.bioSgstAmount AS sgstAmount,
            o.bioBagAmount AS bagAmount,
            COALESCE(o.bioTotalBill, o.scrapTotalBill) AS totalBill
        FROM CompleteOrders o
        WHERE o.id = :id
    """)
    Optional<InvoiceResponse> findInvoiceDetailsByOrderId(
            @Param("id") Long completeOrderId
    );

    @Query(nativeQuery = true, value = """
        SELECT
            co.id AS id,
            co.order_type AS orderType,
            soc.scrap_weight AS weight,
            soc.scrap_price AS price,
            soc.total_price AS totalPrice,

            st.id AS typeId,
            st.scrap_name AS typeName,
            st.image AS typeIcon,
            st.is_active AS typeIsActive,
            soc.scrap_price AS typePrice
        FROM backend_completeorders co
        JOIN backend_scraporders so
            ON so.id = co.scrap_order_id
        JOIN backend_scrapordercart soc
            ON soc.scrap_order_id = so.id
            AND soc.is_deleted = false
        JOIN backend_scraptype st
            ON st.id = soc.scrap_type_id
        WHERE co.id = :id
    
        UNION ALL
    
        SELECT
            co.id AS id,
            co.order_type AS orderType,
            NULL AS weight,
            NULL AS price,
            NULL AS totalPrice,
    
            bt.id AS typeId,
            bt.biowaste_name AS typeName,
            bt.image AS typeIcon,
            bt.is_active AS typeIsActive,
            NULL AS typePrice
        FROM backend_completeorders co
        JOIN backend_biowasteorders bo
            ON bo.id = co.biowaste_order_id
        JOIN backend_biowasteordercart boc
            ON boc.biowaste_order_id = bo.id
            AND boc.is_deleted = false
        JOIN backend_biowastetype bt
            ON bt.id = boc.biowaste_type_id
        WHERE co.id = :id
    """)
    List<IOrderCartItemResponse> findAllCartItemsByOrderId(
            @Param("id") Long completeOrderId
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE CompleteOrders o
        SET o.orderStatus = :orderStatus
        WHERE o.id = :id
    """)
    int updateStatusByCompleteOrderId(
            @Param("id") Long completeOrderId,
            @Param("orderStatus") String orderStatus
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE CompleteOrders o
        SET o.cancelRequest = :isCancelled,
            o.orderStatus = :orderStatus
        WHERE o.id = :id
    """)
    int cancelByCompleteOrderId(
            @Param("id") Long completeOrderId,
            @Param("isCancelled") boolean isCancelled,
            @Param("orderStatus") String orderStatus
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE CompleteOrders o
        SET o.isDeleted = :isDeleted
        WHERE o.id = :id
    """)
    int deleteByCompleteOrderId(
            @Param("id") Long completeOrderId,
            @Param("isDeleted") boolean isDeleted
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE CompleteOrders o
        SET o.provider.id = :providerId,
            o.orderStatus = :newOrderStatus
        WHERE o.id = :id
            AND o.isDeleted = false
            AND o.provider.id IS NULL
            AND o.district.id IN :districtIds
            AND o.orderStatus = :expectedOrderStatus
    """)
    int assignProviderIfEligible(
            @Param("id") Long completeOrderId,
            @Param("providerId") Long providerId,
            @Param("districtIds") Set<Long> districtIds,
            @Param("expectedOrderStatus") String expectedOrderStatus,
            @Param("newOrderStatus") String newOrderStatus
    );


    @Modifying
	@Query("""
        UPDATE CompleteOrders o
        SET o.scheduleDate = :scheduledDate
        WHERE o.id = :id
    """)
	int updateScheduledDate(
			@Param("id") Long id,
			@Param("scheduledDate") LocalDate scheduledDate
	);
    
    
    @Modifying
    @Query("""
        UPDATE CompleteOrders o
        SET o.scheduleDate = :scheduleDate
        WHERE o.bwgOrder.id = :orderId
    """)
    int updateScheduledDateByBwgOrderId(
            @Param("orderId") Long orderId,
            @Param("scheduleDate") LocalDate scheduleDate
    );
    

    
    Optional<CompleteOrders> findByBwgOrder_Id(Long bwgOrderId);
}
