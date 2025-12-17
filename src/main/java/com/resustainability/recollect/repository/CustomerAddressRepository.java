package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.ICustomerAddressResponse;
import com.resustainability.recollect.entity.backend.CustomerAddress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
    @Query("""
        SELECT
            ca.id AS id,

            ca.scrapService AS isScrapService,
            ca.scrapLocationActive AS isScrapLocationActive,
            ca.bioWasteService AS isBioWasteService,
            ca.bioWasteLocationActive AS isBioWasteLocationActive,

            ca.residenceType AS residenceType,
            ca.residenceDetails AS residenceDetails,
            ca.landmark AS landmark,
            ca.latitude AS latitude,
            ca.longitude AS longitude,

            ca.isDeleted AS isDeleted,

            c.id AS customerId,
            c.fullName AS fullName,

            r.id AS scrapRegionId,
            r.regionName AS scrapRegionName,

            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,

            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
    
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName

        FROM CustomerAddress ca
        LEFT JOIN ca.customer c
        LEFT JOIN ca.scrapRegion r
        LEFT JOIN r.district d
        LEFT JOIN d.state s
        LEFT JOIN ca.ward w
        LEFT JOIN w.localbody lb
        WHERE ca.isDeleted = false
            AND (:customerId IS NULL OR c.id = :customerId)
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                ca.residenceType LIKE CONCAT(:searchTerm, '%') OR
                ca.residenceDetails LIKE CONCAT(:searchTerm, '%') OR
                ca.landmark LIKE CONCAT(:searchTerm, '%') OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                r.regionName LIKE CONCAT(:searchTerm, '%') OR
                w.wardName LIKE CONCAT(:searchTerm, '%') OR
                d.districtName LIKE CONCAT(:searchTerm, '%') OR
                d.districtCode LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<ICustomerAddressResponse> findAllPaged(
			@Param("customerId") Long customerId,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            ca.id AS id,

            ca.scrapService AS isScrapService,
            ca.scrapLocationActive AS isScrapLocationActive,
            ca.bioWasteService AS isBioWasteService,
            ca.bioWasteLocationActive AS isBioWasteLocationActive,

            ca.residenceType AS residenceType,
            ca.residenceDetails AS residenceDetails,
            ca.landmark AS landmark,
            ca.latitude AS latitude,
            ca.longitude AS longitude,

            ca.isDeleted AS isDeleted,

            c.id AS customerId,
            c.fullName AS fullName,

            r.id AS scrapRegionId,
            r.regionName AS scrapRegionName,

            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,

            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
    
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName

        FROM CustomerAddress ca
        JOIN ca.customer c
        LEFT JOIN ca.scrapRegion r
        LEFT JOIN r.district d
        LEFT JOIN d.state s
        LEFT JOIN ca.ward w
        LEFT JOIN w.localbody lb
        WHERE ca.isDeleted = false
            AND c.id = :customerId
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                ca.residenceType LIKE CONCAT(:searchTerm, '%') OR
                ca.residenceDetails LIKE CONCAT(:searchTerm, '%') OR
                ca.landmark LIKE CONCAT(:searchTerm, '%') OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                r.regionName LIKE CONCAT(:searchTerm, '%') OR
                w.wardName LIKE CONCAT(:searchTerm, '%') OR
                d.districtName LIKE CONCAT(:searchTerm, '%') OR
                d.districtCode LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<ICustomerAddressResponse> findAllPagedIfBelongs(
            @Param("customerId") Long customerId,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            ca.id AS id,

            ca.scrapService AS isScrapService,
            ca.scrapLocationActive AS isScrapLocationActive,
            ca.bioWasteService AS isBioWasteService,
            ca.bioWasteLocationActive AS isBioWasteLocationActive,

            ca.residenceType AS residenceType,
            ca.residenceDetails AS residenceDetails,
            ca.landmark AS landmark,
            ca.latitude AS latitude,
            ca.longitude AS longitude,

            ca.isDeleted AS isDeleted,

            c.id AS customerId,
            c.fullName AS fullName,

            r.id AS scrapRegionId,
            r.regionName AS scrapRegionName,

            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,

            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
    
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName

        FROM CustomerAddress ca
        LEFT JOIN ca.customer c
        LEFT JOIN ca.scrapRegion r
        LEFT JOIN r.district d
        LEFT JOIN d.state s
        LEFT JOIN ca.ward w
        LEFT JOIN w.localbody lb
        WHERE ca.id = :id
    """)
    Optional<ICustomerAddressResponse> findByCustomerAddressId(
            @Param("id") Long customerAddressId
    );

    @Query("""
        SELECT
            ca.id AS id,

            ca.scrapService AS isScrapService,
            ca.scrapLocationActive AS isScrapLocationActive,
            ca.bioWasteService AS isBioWasteService,
            ca.bioWasteLocationActive AS isBioWasteLocationActive,

            ca.residenceType AS residenceType,
            ca.residenceDetails AS residenceDetails,
            ca.landmark AS landmark,
            ca.latitude AS latitude,
            ca.longitude AS longitude,

            ca.isDeleted AS isDeleted,

            c.id AS customerId,
            c.fullName AS fullName,

            r.id AS scrapRegionId,
            r.regionName AS scrapRegionName,

            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,

            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
    
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName

        FROM CustomerAddress ca
        JOIN ca.customer c
        LEFT JOIN ca.scrapRegion r
        LEFT JOIN r.district d
        LEFT JOIN d.state s
        LEFT JOIN ca.ward w
        LEFT JOIN w.localbody lb
        WHERE ca.id = :id
        AND c.id = :customerId
    """)
    Optional<ICustomerAddressResponse> findByCustomerAddressIdIfBelongs(
            @Param("customerId") Long customerId,
            @Param("id") Long customerAddressId
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE CustomerAddress ca
        SET ca.isDeleted = :isDeleted
        WHERE ca.id = :id
    """)
    int deleteCustomerAddressById(
            @Param("id") Long customerAddressId,
            @Param("isDeleted") boolean isDeleted
    );
}
