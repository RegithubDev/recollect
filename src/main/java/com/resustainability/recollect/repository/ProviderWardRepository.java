package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderWardResponse;
import com.resustainability.recollect.entity.backend.ProviderWard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderWardRepository extends JpaRepository<ProviderWard, Long> {

    @Query("""
        SELECT
            pw.id AS id,
            pw.isActive AS isActive,
            w.id AS wardId,
            p.id AS providerId
        FROM ProviderWard pw
        JOIN pw.ward w
        JOIN pw.provider p
        WHERE (:providerId IS NULL OR p.id = :providerId)
          AND (:wardId IS NULL OR w.id = :wardId)
          AND pw.isActive = true
    """)
    Page<IProviderWardResponse> list(
            @Param("providerId") Long providerId,
            @Param("wardId") Long wardId,
            Pageable pageable
    );

    @Query("""
        SELECT
            pw.id AS id,
            pw.isActive AS isActive,
            w.id AS wardId,
            p.id AS providerId
        FROM ProviderWard pw
        JOIN pw.ward w
        JOIN pw.provider p
        WHERE pw.id = :id
    """)
    IProviderWardResponse findDetails(@Param("id") Long id);


    @Modifying
    @Query("""
        UPDATE ProviderWard pw
        SET pw.isActive = :isActive
        WHERE pw.id = :id
    """)
    int deleteProviderWard(@Param("id") Long id, @Param("isActive") boolean isActive);

    boolean existsByProvider_IdAndWard_Id(Long providerId, Long wardId);
}
