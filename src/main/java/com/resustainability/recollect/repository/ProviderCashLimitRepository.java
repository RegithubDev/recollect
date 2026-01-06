package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderCashLimitResponse;
import com.resustainability.recollect.entity.backend.ProviderCashLimit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProviderCashLimitRepository extends JpaRepository<ProviderCashLimit, Long> {
    @Query("""
        SELECT
            pcl.id AS id,
            pcl.cashLimit AS cashLimit,
            pcl.provider.id AS providerId
        FROM ProviderCashLimit pcl
        WHERE pcl.provider.id IN :providerIds
    """)
    List<IProviderCashLimitResponse> listAllProvidersCashLimit(
            @Param("providerIds") Set<Long> providerIds
    );

    @Query("""
        SELECT
            pcl.id AS id,
            pcl.cashLimit AS cashLimit,
            pcl.provider.id AS providerId
        FROM ProviderCashLimit pcl
        WHERE pcl.provider.id = :providerId
    """)
    List<IProviderCashLimitResponse> listAllProviderCashLimitById(
            @Param("providerId") Long providerId
    );
}
