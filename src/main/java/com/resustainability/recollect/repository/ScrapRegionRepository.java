package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IScrapRegionResponse;
import com.resustainability.recollect.entity.backend.ScrapRegion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScrapRegionRepository extends JpaRepository<ScrapRegion, Long> {
    @Query("""
        SELECT
            sr.id AS id,
            sr.regionName AS name,
            sr.regionWeekdayCurrent AS currentWeekday,
            sr.regionWeekdayNext AS nextWeekday,
            sr.isActive AS isActive,
            sr.isDeleted AS isDeleted,
    
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
    		sr.borderPolygon as borderPolygon,
            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            c.id AS countryId,
            c.countryName AS countryName
        FROM ScrapRegion sr
        LEFT JOIN sr.district d
        LEFT JOIN d.state s
        LEFT JOIN s.country c
        WHERE sr.isDeleted = false
        AND
            (:searchTerm IS NULL OR :searchTerm = '' OR
                sr.regionName LIKE CONCAT(:searchTerm, '%') OR
                d.districtName LIKE CONCAT(:searchTerm, '%') OR
                s.stateName LIKE CONCAT(:searchTerm, '%') OR
                c.countryName LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IScrapRegionResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            sr.id AS id,
            sr.regionName AS name,
            sr.regionWeekdayCurrent AS currentWeekday,
            sr.regionWeekdayNext AS nextWeekday,
            sr.isActive AS isActive,
            sr.isDeleted AS isDeleted,
    
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
    
            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            c.id AS countryId,
            c.countryName AS countryName
        FROM ScrapRegion sr
        LEFT JOIN sr.district d
        LEFT JOIN d.state s
        LEFT JOIN s.country c
        WHERE sr.id = :scrapRegionId
        AND sr.isDeleted = false
    """)
    Optional<IScrapRegionResponse> findByScrapRegionId(
            @Param("scrapRegionId") Long scrapRegionId
    );

    @Query("""
        SELECT sr.borderPolygon
        FROM ScrapRegion sr
        WHERE sr.id = :scrapRegionId
    """)
    Optional<String> findBorderByScrapRegionId(
            @Param("scrapRegionId") Long scrapRegionId
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE ScrapRegion s
        SET s.isActive =
                CASE
                    WHEN s.isActive = true THEN false
                    ELSE true
                END
        WHERE s.id = :scrapRegionId
    """)
    int toggleActiveStatusById(@Param("scrapRegionId") Long scrapRegionId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE ScrapRegion s
        SET s.isActive = :isActive,
            s.isDeleted = :isDeleted
        WHERE s.id = :scrapRegionId
    """)
    int deleteScrapRegionById(
            @Param("scrapRegionId") Long scrapRegionId,
            @Param("isActive") boolean isActive,
            @Param("isDeleted") boolean isDeleted
    );
}
