package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderTeamResponse;
import com.resustainability.recollect.entity.backend.ProviderTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProviderTeamRepository extends JpaRepository<ProviderTeam, Long> {

    boolean existsByTeamNameIgnoreCase(String teamName);

    @Query("""
            SELECT 
                t.id AS id,
                t.teamName AS teamName,
                t.isActive AS isActive,
                t.isDeleted AS isDeleted,
                t.createdAt AS createdAt
            FROM ProviderTeam t
            WHERE t.isDeleted = false
            AND (
                :search IS NULL OR :search = '' OR
                t.teamName LIKE CONCAT(:search, '%')
            )
        """)
    Page<IProviderTeamResponse> findAllPaged(String search, Pageable pageable);

    @Query("""
            SELECT 
                t.id AS id,
                t.teamName AS teamName,
                t.isActive AS isActive,
                t.isDeleted AS isDeleted,
                t.createdAt AS createdAt
            FROM ProviderTeam t
            WHERE t.id = :teamId
        """)
    IProviderTeamResponse findDetails(Long teamId);

    @Modifying
    @Query("""
            UPDATE ProviderTeam t
            SET t.isActive = :isActive,
                t.isDeleted = :isDeleted
            WHERE t.id = :teamId
        """)
    int softDelete(Long teamId, boolean isActive, boolean isDeleted);
}
