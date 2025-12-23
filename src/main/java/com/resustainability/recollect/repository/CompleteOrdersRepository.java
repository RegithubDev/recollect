package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IOrderHistoryResponse;
import com.resustainability.recollect.entity.backend.CompleteOrders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompleteOrdersRepository extends JpaRepository<CompleteOrders, Long> {
    @Query("""
        SELECT
            o.id AS id,
            c.id AS customerId,
            c.fullName AS fullName,
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
            AND (so.id IS NOT NULL OR bo.id IS NOT NULL)
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                COALESCE(so.orderCode, bo.orderCode) LIKE CONCAT(:searchTerm, '%') OR
                o.orderType LIKE CONCAT(:searchTerm, '%') OR
                o.orderStatus LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IOrderHistoryResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            o.id AS id,
            c.id AS customerId,
            c.fullName AS fullName,
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
            AND (so.id IS NOT NULL OR bo.id IS NOT NULL)
            AND c.id = :customerId
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                COALESCE(so.orderCode, bo.orderCode) LIKE CONCAT(:searchTerm, '%') OR
                o.orderType LIKE CONCAT(:searchTerm, '%') OR
                o.orderStatus LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IOrderHistoryResponse> findAllPagedIfBelongs(
            @Param("customerId") Long customerId,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            o.id AS id,
            so.id AS scrapOrderId,
            sr.id AS scrapRegionId,
            sr.regionName AS scrapRegionName,
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
        WHERE (so.id IS NOT NULL OR bo.id IS NOT NULL)
        AND o.id = :id
        AND c.id = :customerId
    """)
    Optional<IOrderHistoryResponse> findByCompleteOrderIdIfBelongs(
            @Param("customerId") Long customerId,
            @Param("id") Long completeOrderId
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
}
