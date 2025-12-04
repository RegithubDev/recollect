package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderTeamAllotedWardResponse;
import com.resustainability.recollect.entity.backend.ProviderTeamAllotedWards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderTeamAllotedWardsRepository extends JpaRepository<ProviderTeamAllotedWards, Long> {

    boolean existsByTeam_IdAndWard_Id(Long teamId, Long wardId);

    @Query("""
            SELECT 
                a.id AS id,
                a.monday AS monday,
                a.tuesday AS tuesday,
                a.wednesday AS wednesday,
                a.thursday AS thursday,
                a.friday AS friday,
                a.saturday AS saturday,
                a.sunday AS sunday,
                a.createdAt AS createdAt,
                t.id AS teamId,
                t.teamName AS teamName,
                w.id AS wardId,
                w.wardName AS wardName
            FROM ProviderTeamAllotedWards a
            JOIN a.team t
            JOIN a.ward w
            WHERE (:search IS NULL OR :search = '' 
                OR t.teamName LIKE CONCAT(:search, '%') 
                OR w.wardName LIKE CONCAT(:search, '%'))
        """)
    Page<IProviderTeamAllotedWardResponse> list(String search, Pageable pageable);

    @Query("""
            SELECT 
                a.id AS id,
                a.monday AS monday,
                a.tuesday AS tuesday,
                a.wednesday AS wednesday,
                a.thursday AS thursday,
                a.friday AS friday,
                a.saturday AS saturday,
                a.sunday AS sunday,
                a.createdAt AS createdAt,
                t.id AS teamId,
                t.teamName AS teamName,
                w.id AS wardId,
                w.wardName AS wardName
            FROM ProviderTeamAllotedWards a
            JOIN a.team t
            JOIN a.ward w
            WHERE a.id = :id
        """)
    IProviderTeamAllotedWardResponse getDetails(Long id);
}
