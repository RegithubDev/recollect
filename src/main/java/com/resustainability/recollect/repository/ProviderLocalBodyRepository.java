package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderLocalBodyResponse;
import com.resustainability.recollect.entity.backend.ProviderLocalBody;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderLocalBodyRepository extends JpaRepository<ProviderLocalBody, Long> {

    @Query("""
        SELECT
            plb.id AS id,
            plb.isActive AS isActive,
            lb.id AS localBodyId,
            p.id AS providerId
        FROM ProviderLocalBody plb
        JOIN plb.localBody lb
        JOIN plb.provider p
        WHERE (:providerId IS NULL OR p.id = :providerId)
          AND (:localBodyId IS NULL OR lb.id = :localBodyId)
          AND plb.isActive = true
    """)
    Page<IProviderLocalBodyResponse> list(
            @Param("providerId") Long providerId,
            @Param("localBodyId") Long localBodyId,
            Pageable pageable
    );

    @Query("""
        SELECT
            plb.id AS id,
            plb.isActive AS isActive,
            lb.id AS localBodyId,
            p.id AS providerId
        FROM ProviderLocalBody plb
        JOIN plb.localBody lb
        JOIN plb.provider p
        WHERE plb.id = :id
    """)
    IProviderLocalBodyResponse findDetails(@Param("id") Long id);


    @Modifying
    @Query("""
        UPDATE ProviderLocalBody plb
        SET plb.isActive = :isActive
        WHERE plb.id = :id
    """)
    int deleteProviderLocalBody(@Param("id") Long id, @Param("isActive") boolean isActive);

    boolean existsByProvider_IdAndLocalBody_Id(Long providerId, Long localBodyId);
}
