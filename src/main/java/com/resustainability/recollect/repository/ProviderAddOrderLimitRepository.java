package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderAddOrderLimitResponse;
import com.resustainability.recollect.entity.backend.ProviderAddOrderLimit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProviderAddOrderLimitRepository extends JpaRepository<ProviderAddOrderLimit, Long> {
    @Query("""
        SELECT
            p.id AS id,
            p.maxLimit AS maxLimit,
            p.currentLimit AS currentLimit,
            pr.id AS providerId
        FROM ProviderAddOrderLimit p
        JOIN p.provider pr
        WHERE (:providerId IS NULL OR pr.id = :providerId)
        AND (
            :searchTerm IS NULL OR :searchTerm = '' OR
            CAST(p.maxLimit AS string) LIKE CONCAT(:searchTerm, '%') OR
            CAST(p.currentLimit AS string) LIKE CONCAT(:searchTerm, '%')
        )
    """)
    Page<IProviderAddOrderLimitResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            @Param("providerId") Long providerId,
            Pageable pageable
    );

    @Query("""
        SELECT
            p.id AS id,
            p.maxLimit AS maxLimit,
            p.currentLimit AS currentLimit,
            pr.id AS providerId
        FROM ProviderAddOrderLimit p
        JOIN p.provider pr
        WHERE pr.id IN :providerIds
    """)
    List<IProviderAddOrderLimitResponse> listAllProvidersAddOrderLimit(
            @Param("providerIds") Set<Long> providerIds
    );

    @Query("""
        SELECT
            p.id AS id,
            p.maxLimit AS maxLimit,
            p.currentLimit AS currentLimit,
            pr.id AS providerId
        FROM ProviderAddOrderLimit p
        JOIN p.provider pr
        WHERE pr.id = :providerId
    """)
    List<IProviderAddOrderLimitResponse> listAllProviderAddOrderLimitById(
            @Param("providerId") Long providerId
    );

    @Query("""
        SELECT
            p.id AS id,
            p.maxLimit AS maxLimit,
            p.currentLimit AS currentLimit,
            pr.id AS providerId
        FROM ProviderAddOrderLimit p
        JOIN p.provider pr
        WHERE p.id = :id
    """)
    Optional<IProviderAddOrderLimitResponse> findDetailsById(@Param("id") Long id);

    @Query("""
        SELECT
        CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM ProviderAddOrderLimit p
        WHERE p.provider.id = :providerId
    """)
    boolean existsByProviderId(@Param("providerId") Long providerId);

    @Query("""
        SELECT
        CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM ProviderAddOrderLimit p
        WHERE p.provider.id = :providerId
        AND p.id <> :id
    """)
    boolean existsByProviderIdExceptId(
            @Param("providerId") Long providerId,
            @Param("id") Long id
    );
}
