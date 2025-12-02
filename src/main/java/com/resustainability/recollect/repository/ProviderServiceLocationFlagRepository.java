package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderServiceLocationFlagResponse;
import com.resustainability.recollect.entity.backend.ProviderServiceLocationFlag;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProviderServiceLocationFlagRepository
        extends JpaRepository<ProviderServiceLocationFlag, Long> {

    @Query("""
        SELECT 
            pslf.id AS id,

            p.id AS providerId,
            p.fullName AS providerName,

            pslf.allScrapDistrict AS allScrapDistrict,
            pslf.allBioDistrict AS allBioDistrict,
            pslf.allScrapRegions AS allScrapRegions,
            pslf.allLocalBodies AS allLocalBodies,
            pslf.allWards AS allWards

        FROM ProviderServiceLocationFlag pslf
        JOIN pslf.provider p
        WHERE (:providerId IS NULL OR p.id = :providerId)
    """)
    Page<IProviderServiceLocationFlagResponse> list(
            @Param("providerId") Long providerId,
            Pageable pageable
    );

    @Query("""
        SELECT 
            pslf.id AS id,

            p.id AS providerId,
            p.fullName AS providerName,

            pslf.allScrapDistrict AS allScrapDistrict,
            pslf.allBioDistrict AS allBioDistrict,
            pslf.allScrapRegions AS allScrapRegions,
            pslf.allLocalBodies AS allLocalBodies,
            pslf.allWards AS allWards

        FROM ProviderServiceLocationFlag pslf
        JOIN pslf.provider p
        WHERE pslf.id = :id
    """)
    IProviderServiceLocationFlagResponse findDetails(@Param("id") Long id);

    boolean existsByProvider_Id(Long providerId);
}
