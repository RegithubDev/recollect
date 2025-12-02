package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderScrapRegionResponse;
import com.resustainability.recollect.entity.backend.ProviderScrapRegion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderScrapRegionRepository 
        extends JpaRepository<ProviderScrapRegion, Long> {

    @Query("""
        SELECT 
            psr.id AS id,
            psr.isActive AS isActive,

            p.id AS providerId,
            p.fullName AS providerName,

            sr.id AS scrapRegionId,
            sr.regionName AS scrapRegionName

        FROM ProviderScrapRegion psr
        JOIN psr.provider p
        JOIN psr.scrapRegion sr
        WHERE (:providerId IS NULL OR p.id = :providerId)
        AND (:scrapRegionId IS NULL OR sr.id = :scrapRegionId)
        AND psr.isActive = true
    """)
    Page<IProviderScrapRegionResponse> list(
            @Param("providerId") Long providerId,
            @Param("scrapRegionId") Long scrapRegionId,
            Pageable pageable
    );

    @Query("""
        SELECT 
            psr.id AS id,
            psr.isActive AS isActive,

            p.id AS providerId,
            p.fullName AS providerName,

            sr.id AS scrapRegionId,
            sr.regionName AS scrapRegionName

        FROM ProviderScrapRegion psr
        JOIN psr.provider p
        JOIN psr.scrapRegion sr
        WHERE psr.id = :id
    """)
    IProviderScrapRegionResponse findDetails(@Param("id") Long id);

    @Modifying
    @Query("""
        UPDATE ProviderScrapRegion psr 
        SET psr.isActive = :isActive 
        WHERE psr.id = :id
    """)
    int deleteProviderScrapRegion(
        @Param("id") Long id, 
        @Param("isActive") boolean isActive
    );

    boolean existsByProvider_IdAndScrapRegion_Id(Long providerId, Long scrapRegionId);
}
