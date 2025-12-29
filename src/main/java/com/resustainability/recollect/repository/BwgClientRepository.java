package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IBwgClientResponse;
import com.resustainability.recollect.entity.backend.BwgClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BwgClientRepository extends JpaRepository<BwgClient, Long> {


	@Query("""
		    SELECT
		        c.id AS id,
		        c.fullName AS fullName,
		        c.email AS email,
		        c.phoneNumber AS phoneNumber,
		        c.contract AS contract,
		        c.dateJoined AS dateJoined,
		        c.isActive AS isActive,
		        c.isDeleted AS isDeleted,
		        c.bioOrder AS bioOrder,
		        c.scrapOrder AS scrapOrder,
		        c.address AS address,
		        c.familyNumber AS familyNumber,
		        c.clientPrice AS clientPrice,
		        c.clientCgst AS clientCgst,
		        c.clientSgst AS clientSgst,
		        c.verificationStatus AS verificationStatus,

		        d.id AS districtId,
		        d.districtName AS districtName,

		        s.id AS stateId,
		        s.stateName AS stateName,

		        w.id AS wardId,
		        w.wardName AS wardName,

		        sr.id AS scrapRegionId,
		        sr.regionName AS scrapRegionName,

		        c.alternateNumber AS alternateNumber,
		        c.clientCategory AS clientCategory,
		        c.remark AS remark,
		        c.serviceType AS serviceType,

		        c.contractEndDate AS contractEndDate,
		        c.contractStartDate AS contractStartDate,

		        ar.id AS approveRequestId,
		        c.requestApproved AS requestApproved,

		        c.gstName AS gstName,
		        c.gstNo AS gstNo,
		        c.accountNumber AS accountNumber,
		        c.ifscCode AS ifscCode,

		        c.collectionFrequency AS collectionFrequency,
		        c.monthlyContract AS monthlyContract

		    FROM BwgClient c
		    LEFT JOIN c.district d
		    LEFT JOIN c.state s
		    LEFT JOIN c.ward w
		    LEFT JOIN c.scrapRegion sr
		    LEFT JOIN c.approveRequest ar

		    WHERE c.isDeleted = false
		    AND (
		        :searchTerm IS NULL OR :searchTerm = '' OR
		        c.fullName LIKE CONCAT(:searchTerm, '%') OR
		        c.phoneNumber LIKE CONCAT(:searchTerm, '%')
		    )
		""")
		Page<IBwgClientResponse> findAllPaged(
		        @Param("searchTerm") String searchTerm,
		        Pageable pageable
		);



	@Query("""
		    SELECT
		        c.id AS id,
		        c.fullName AS fullName,
		        c.email AS email,
		        c.phoneNumber AS phoneNumber,
		        c.contract AS contract,
		        c.dateJoined AS dateJoined,
		        c.isActive AS isActive,
		        c.isDeleted AS isDeleted,
		        c.bioOrder AS bioOrder,
		        c.scrapOrder AS scrapOrder,
		        c.address AS address,
		        c.familyNumber AS familyNumber,
		        c.clientPrice AS clientPrice,
		        c.clientCgst AS clientCgst,
		        c.clientSgst AS clientSgst,
		        c.verificationStatus AS verificationStatus,

		        d.id AS districtId,
		        d.districtName AS districtName,

		        s.id AS stateId,
		        s.stateName AS stateName,

		        w.id AS wardId,
		        w.wardName AS wardName,

		        sr.id AS scrapRegionId,
		        sr.regionName AS scrapRegionName,

		        c.alternateNumber AS alternateNumber,
		        c.clientCategory AS clientCategory,
		        c.remark AS remark,
		        c.serviceType AS serviceType,

		        c.contractEndDate AS contractEndDate,
		        c.contractStartDate AS contractStartDate,

		        ar.id AS approveRequestId,
		        c.requestApproved AS requestApproved,

		        c.gstName AS gstName,
		        c.gstNo AS gstNo,
		        c.accountNumber AS accountNumber,
		        c.ifscCode AS ifscCode,

		        c.collectionFrequency AS collectionFrequency,
		        c.monthlyContract AS monthlyContract

		    FROM BwgClient c
		    LEFT JOIN c.district d
		    LEFT JOIN c.state s
		    LEFT JOIN c.ward w
		    LEFT JOIN c.scrapRegion sr
		    LEFT JOIN c.approveRequest ar

		    WHERE c.id = :id
		""")
		Optional<IBwgClientResponse> findByClientId(@Param("id") Long id);


    @Modifying
    @Query("""
        UPDATE BwgClient c
        SET c.isActive = :isActive,
            c.isDeleted = :isDeleted
        WHERE c.id = :id
    """)
    int deleteById(Long id, boolean isActive, boolean isDeleted);

    @Modifying
    @Query("""
        UPDATE BwgClient c
        SET c.contract = :contract
        WHERE c.id = :id
    """)
    int updateContract(Long id, String contract);
    
    @Query("""
            SELECT c.contract
            FROM BwgClient c
            WHERE c.id = :id
        """)
        Optional<String> findContractById(Long id);
    

}
