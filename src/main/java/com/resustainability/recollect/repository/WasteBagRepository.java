package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IWasteBagResponse;
import com.resustainability.recollect.entity.backend.WasteBag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WasteBagRepository extends JpaRepository<WasteBag, Long> {

    @Query("""
            SELECT 
                w.id AS id,
                w.bagSize AS bagSize,
                w.bagPrice AS bagPrice,
                w.bagCgst AS bagCgst,
                w.bagSgst AS bagSgst,
                w.isActive AS isActive,
                w.isBwg AS isBwg,
                s.id AS stateId,
                s.stateName AS stateName,
                c.id AS countryId,
                c.countryName AS countryName
            FROM WasteBag w
            JOIN w.state s
            JOIN s.country c
            WHERE (:stateId IS NULL OR w.state.id = :stateId)
            AND (:searchTerm IS NULL OR :searchTerm = '' OR
                 w.bagSize LIKE CONCAT(:searchTerm, '%'))
        """)
    Page<IWasteBagResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            @Param("stateId") Long stateId,
            Pageable pageable
    );

    @Query("""
            SELECT 
                w.id AS id,
                w.bagSize AS bagSize,
                w.bagPrice AS bagPrice,
                w.bagCgst AS bagCgst,
                w.bagSgst AS bagSgst,
                w.isActive AS isActive,
                w.isBwg AS isBwg,
                s.id AS stateId,
                s.stateName AS stateName,
                c.id AS countryId,
                c.countryName AS countryName
            FROM WasteBag w
            JOIN w.state s
            JOIN s.country c
            WHERE w.id = :id
        """)
    Optional<IWasteBagResponse> findByWasteBagId(@Param("id") Long id);

    @Query("""
            SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END
            FROM WasteBag w
            WHERE w.bagSize = :bagSize AND w.state.id = :stateId
        """)
    boolean existsByBagSizeAndState(@Param("bagSize") String bagSize, @Param("stateId") Long stateId);
}
