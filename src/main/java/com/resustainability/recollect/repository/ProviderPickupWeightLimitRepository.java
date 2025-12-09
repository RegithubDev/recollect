package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderPickupWeightLimitResponse;
import com.resustainability.recollect.entity.backend.ProviderPickupWeightLimit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderPickupWeightLimitRepository
        extends JpaRepository<ProviderPickupWeightLimit, Long> {

    @Query("""
        SELECT
            p.id AS id,
            p.weightLimit AS weightLimit,
            pr.id AS providerId,
            pr.fullName AS providerName
        FROM ProviderPickupWeightLimit p
        JOIN p.provider pr
        WHERE (:providerId IS NULL OR pr.id = :providerId)
    """)
    Page<IProviderPickupWeightLimitResponse> findAllPaged(
            @Param("providerId") Long providerId,
            Pageable pageable
    );


    @Query("""
        SELECT
            p.id AS id,
            p.weightLimit AS weightLimit,
            pr.id AS providerId,
            pr.fullName AS providerName
        FROM ProviderPickupWeightLimit p
        JOIN p.provider pr
        WHERE p.id = :id
    """)
    Optional<IProviderPickupWeightLimitResponse> findByIdDetails(@Param("id") Long id);


    boolean existsByProvider_Id(Long providerId);
}
