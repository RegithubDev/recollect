package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IBwgBagPriceResponse;
import com.resustainability.recollect.entity.backend.BwgBagPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BwgBagPriceRepository extends JpaRepository<BwgBagPrice, Long> {

    @Query("""
        SELECT
            b.id AS id,
            b.bagSize AS bagSize,
            b.bagPrice AS bagPrice,
            b.bagCgst AS bagCgst,
            b.bagSgst AS bagSgst,
            b.isActive AS isActive,
            c.id AS clientId,
            c.fullName AS clientName
        FROM BwgBagPrice b
        JOIN b.client c
        WHERE (:clientId IS NULL OR c.id = :clientId)
        AND (:searchTerm IS NULL OR :searchTerm = '' OR
             b.bagSize LIKE CONCAT(:searchTerm, '%'))
    """)
    Page<IBwgBagPriceResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            @Param("clientId") Long clientId,
            Pageable pageable
    );

    @Query("""
        SELECT
            b.id AS id,
            b.bagSize AS bagSize,
            b.bagPrice AS bagPrice,
            b.bagCgst AS bagCgst,
            b.bagSgst AS bagSgst,
            b.isActive AS isActive,
            c.id AS clientId,
            c.fullName AS clientName
        FROM BwgBagPrice b
        JOIN b.client c
        WHERE b.id = :id
    """)
    Optional<IBwgBagPriceResponse> findByBagPriceId(@Param("id") Long id);

    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
        FROM BwgBagPrice b
        WHERE b.bagSize = :bagSize
        AND b.client.id = :clientId
    """)
    boolean existsByBagSizeAndClient(
            @Param("bagSize") String bagSize,
            @Param("clientId") Long clientId
    );
}
