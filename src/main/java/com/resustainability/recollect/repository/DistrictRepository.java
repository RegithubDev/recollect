package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IDistrictResponse;
import com.resustainability.recollect.entity.backend.District;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("""
            SELECT 
                d.id AS id,
                d.districtName AS name,
                d.districtCode AS code,
                d.isActive AS isActive,
                d.isDeleted AS isDeleted,
                s.id AS stateId,
                s.stateName AS stateName,
                c.id AS countryId,
                c.countryName AS countryName
            FROM District d
            JOIN d.state s
            JOIN s.country c
            WHERE d.isDeleted = false
            AND (:stateId IS NULL OR d.state.id = :stateId)
            AND (:countryId IS NULL OR c.id = :countryId)
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                d.districtName LIKE CONCAT(:searchTerm, '%') OR
                d.districtCode LIKE CONCAT(:searchTerm, '%')
            )
        """)
    Page<IDistrictResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            @Param("stateId") Long stateId,
            @Param("countryId") Long countryId,
            Pageable pageable
    );


    @Query("""
            SELECT 
                d.id AS id,
                d.districtName AS name,
                d.districtCode AS code,
                d.isActive AS isActive,
                d.isDeleted AS isDeleted,
                s.id AS stateId,
                s.stateName AS stateName,
                c.id AS countryId,
                c.countryName AS countryName
            FROM District d
            JOIN d.state s
            JOIN s.country c
            WHERE d.id = :id
        """)
    Optional<IDistrictResponse> findByDistrictId(@Param("id") Long districtId);

    @Query("""
            SELECT 
            CASE WHEN COUNT(d) > 0 THEN true ELSE false END
            FROM District d
            WHERE d.districtName = :name
        """)
    boolean existsByName(@Param("name") String code);

    @Query("""
            SELECT 
            CASE WHEN COUNT(d) > 0 THEN true ELSE false END
            FROM District d
            WHERE d.districtCode = :code
        """)
    boolean existsByCode(@Param("code") String code);


    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE District d
            SET d.isActive = :isActive,
                d.isDeleted = :isDeleted
            WHERE d.id = :id
        """)
    int deleteDistrictById(@Param("id") Long districtId,@Param("isActive") boolean isActive, @Param("isDeleted") boolean isDeleted );
}
