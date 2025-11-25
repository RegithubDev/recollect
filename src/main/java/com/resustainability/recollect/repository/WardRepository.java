package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IWardResponse;
import com.resustainability.recollect.entity.backend.Ward;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {

    @Query("""
            SELECT
                w.id AS id,
                w.wardNo AS wardNo,
                w.wardName AS wardName,
                w.wardWeekdayCurrent AS wardWeekdayCurrent,
                w.wardWeekdayNext AS wardWeekdayNext,
                w.isActive AS isActive,
                w.isDeleted AS isDeleted,
                lb.id AS localBodyId,
                lb.localBodyName AS localBodyName,
                d.id AS districtId,
                d.districtName AS districtName,
                s.id AS stateId,
                s.stateName AS stateName,
                c.id AS countryId,
                c.countryName AS countryName
            FROM Ward w
            JOIN w.localbody lb
            JOIN lb.district d
            JOIN d.state s
            JOIN s.country c
            WHERE w.isDeleted = false
            AND (:localbodyId IS NULL OR w.localbody.id = :localbodyId)
            AND (:districtId IS NULL OR d.id = :districtId)
            AND (:stateId IS NULL OR s.id = :stateId)
            AND (:countryId IS NULL OR c.id = :countryId)
            AND (
                :searchTerm IS NULL OR :searchTerm = '' OR
                w.wardName LIKE CONCAT(:searchTerm, '%')
            )
        """)
    Page<IWardResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            @Param("localbodyId") Long localbodyId,
            @Param("districtId") Long districtId,
            @Param("stateId") Long stateId,
            @Param("countryId") Long countryId,
            Pageable pageable
    );


    @Query("""
            SELECT 
                w.id AS id,
                w.wardNo AS wardNo,
                w.wardName AS wardName,
                w.wardWeekdayCurrent AS wardWeekdayCurrent,
                w.wardWeekdayNext AS wardWeekdayNext,
                w.isActive AS isActive,
                w.isDeleted AS isDeleted,
                lb.id AS localBodyId,
                lb.localBodyName AS localBodyName,
                d.id AS districtId,
                d.districtName AS districtName,
                s.id AS stateId,
                s.stateName AS stateName,
                c.id AS countryId,
                c.countryName AS countryName
            FROM Ward w
            JOIN w.localbody lb
            JOIN lb.district d
            JOIN d.state s
            JOIN s.country c
            WHERE w.id = :id
        """)
    Optional<IWardResponse> findWardById(@Param("id") Long wardId);


    @Modifying
    @Query("""
        UPDATE Ward w
        SET w.isActive = :isActive,
            w.isDeleted = :isDeleted
        WHERE w.id = :id
    """)
    int deleteWardById(
            @Param("id") Long wardId,
            @Param("isActive") boolean isActive,
            @Param("isDeleted") boolean isDeleted
    );
}
