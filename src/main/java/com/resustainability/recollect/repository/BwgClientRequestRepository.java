package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IBwgClientRequestResponse;
import com.resustainability.recollect.entity.backend.BwgClientRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BwgClientRequestRepository
        extends JpaRepository<BwgClientRequest, Long> {

    @Query("""
        SELECT
            b.id AS id,
            b.fullName AS fullName,
            b.phoneNumber AS phoneNumber,
            b.alternateNumber AS alternateNumber,
            b.serviceType AS serviceType,
            b.address AS address,
            b.landmark AS landmark,
            b.houseName AS houseName,
            b.houseNumber AS houseNumber,
            b.clientCategory AS clientCategory,
            b.appointmentDate AS appointmentDate,
            b.remark AS remark,
            b.callCenterRemark AS callCenterRemark,
            b.verificationStatus AS verificationStatus,
            b.bioPickup AS bioPickup,
            b.scrapPickup AS scrapPickup,
            b.collectionFrequency AS collectionFrequency,
            b.familyNumber AS familyNumber,
            b.isConfirmed AS isConfirmed,
            b.accountNumber AS accountNumber,
            b.ifscCode AS ifscCode,
            b.createdAt AS createdAt,
            s.id AS stateId,
            s.stateName AS stateName
        FROM BwgClientRequest b
        LEFT JOIN b.state s
        ORDER BY b.createdAt DESC
    """)
    Page<IBwgClientRequestResponse> findAllPaged(Pageable pageable);

    @Query("""
        SELECT
            b.id AS id,
            b.fullName AS fullName,
            b.phoneNumber AS phoneNumber,
            b.alternateNumber AS alternateNumber,
            b.serviceType AS serviceType,
            b.address AS address,
            b.landmark AS landmark,
            b.houseName AS houseName,
            b.houseNumber AS houseNumber,
            b.clientCategory AS clientCategory,
            b.appointmentDate AS appointmentDate,
            b.remark AS remark,
            b.callCenterRemark AS callCenterRemark,
            b.verificationStatus AS verificationStatus,
            b.bioPickup AS bioPickup,
            b.scrapPickup AS scrapPickup,
            b.collectionFrequency AS collectionFrequency,
            b.familyNumber AS familyNumber,
            b.isConfirmed AS isConfirmed,
            b.accountNumber AS accountNumber,
            b.ifscCode AS ifscCode,
            b.createdAt AS createdAt,
            s.id AS stateId,
            s.stateName AS stateName
        FROM BwgClientRequest b
        LEFT JOIN b.state s
        WHERE b.id = :id
    """)
    Optional<IBwgClientRequestResponse> findByRequestId(@Param("id") Long id);
}
