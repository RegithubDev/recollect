package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderDistrictResponse;
import com.resustainability.recollect.entity.backend.ProviderDistrict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProviderDistrictRepository extends JpaRepository<ProviderDistrict, Long> {
    @Query("""
        SELECT
            pd.id AS id,
            pd.scrapAllowed AS scrapAllowed,
            pd.bioAllowed AS bioAllowed,
            pd.isActive AS isActive,

            d.id AS districtId,
            d.districtCode AS districtCode,
            d.districtName AS districtName,

            p.id AS providerId

        FROM ProviderDistrict pd
        JOIN pd.district d
        JOIN pd.provider p
        WHERE (:providerId IS NULL OR p.id = :providerId)
          AND (:districtId IS NULL OR d.id = :districtId)
          AND pd.isActive = true
    """)
    Page<IProviderDistrictResponse> list(
            @Param("providerId") Long providerId,
            @Param("districtId") Long districtId,
            Pageable pageable
    );

    @Query("""
        SELECT
            pd.id AS id,
            pd.scrapAllowed AS scrapAllowed,
            pd.bioAllowed AS bioAllowed,
            pd.isActive AS isActive,

            d.id AS districtId,
            d.districtCode AS districtCode,
            d.districtName AS districtName,

            p.id AS providerId

        FROM ProviderDistrict pd
        JOIN pd.district d
        JOIN pd.provider p
        WHERE p.id IN :providerIds
          AND pd.isActive = true
    """)
    List<IProviderDistrictResponse> listAllActiveProviderDistricts(
            @Param("providerIds") Set<Long> providerIds
    );

    @Query("""
        SELECT
            pd.id AS id,
            pd.scrapAllowed AS scrapAllowed,
            pd.bioAllowed AS bioAllowed,
            pd.isActive AS isActive,

            d.id AS districtId,
            d.districtCode AS districtCode,
            d.districtName AS districtName,

            p.id AS providerId

        FROM ProviderDistrict pd
        JOIN pd.district d
        JOIN pd.provider p
        WHERE p.id = :providerId
          AND pd.isActive = true
    """)
    List<IProviderDistrictResponse> listAllActiveProviderDistrictsById(
            @Param("providerId") Long providerId
    );

    @Query("""
        SELECT
            d.id AS districtId
        FROM ProviderDistrict pd
        JOIN pd.district d
        JOIN pd.provider p
        WHERE p.id = :providerId
          AND pd.isActive = true
    """)
    Set<Long> listAllActiveProviderDistrictIds(
            @Param("providerId") Long providerId
    );

    @Query("""
        SELECT
            pd.id AS id,
            pd.scrapAllowed AS scrapAllowed,
            pd.bioAllowed AS bioAllowed,
            pd.isActive AS isActive,

            d.id AS districtId,
            d.districtCode AS districtCode,
            d.districtName AS districtName,

            p.id AS providerId

        FROM ProviderDistrict pd
        JOIN pd.district d
        JOIN pd.provider p
        WHERE pd.id = :id
    """)
    IProviderDistrictResponse findDetails(@Param("id") Long id);


    @Modifying
    @Query("""
        UPDATE ProviderDistrict pd
        SET pd.isActive = :isActive
        WHERE pd.id = :id
    """)
    int deleteProviderDistrict(@Param("id") Long id, @Param("isActive") boolean isActive);

    boolean existsByProvider_IdAndDistrict_Id(Long providerId, Long districtId);
}
