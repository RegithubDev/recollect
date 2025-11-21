package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.ICountryResponse;
import com.resustainability.recollect.entity.backend.Country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("""
        SELECT
            c.id AS id,
            c.countryName AS name,
            c.countryCode AS code,
            c.countryImage AS icon,
            c.isActive AS isActive,
            c.isDeleted AS isDeleted
        FROM Country c
        WHERE c.isDeleted = false
        AND
            (:searchTerm IS NULL OR :searchTerm = '' OR
                c.countryName LIKE CONCAT(:searchTerm, '%') OR
                c.countryCode LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<ICountryResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            c.id AS id,
            c.countryName AS name,
            c.countryCode AS code,
            c.countryImage AS icon,
            c.isActive AS isActive,
            c.isDeleted AS isDeleted
        FROM Country c
        WHERE c.id = :id
    """)
    Optional<ICountryResponse> findByCountryId(@Param("id") Long countryId);

    @Query("""
        SELECT
        CASE WHEN COUNT(c) > 0
            THEN true
            ELSE false
        END
        FROM Country c
        WHERE c.countryCode = :code
    """)
    boolean existsByCode(@Param("code") String code);

    @Query("""
        SELECT c.countryImage
        FROM Country c
        WHERE c.id = :id
    """)
    Optional<String> findCountryImageById(@Param("id") Long countryId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Country c
        SET c.countryImage = :filePath
        WHERE c.id = :id
    """)
    int updateCountryImageById(
            @Param("id") Long countryId,
            @Param("filePath") String filePath
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Country c
        SET c.isActive = :isActive,
            c.isDeleted = :isDeleted
        WHERE c.id = :id
    """)
    int deleteCountryById(@Param("id") Long countryId,@Param("isActive") boolean isActive, @Param("isDeleted") boolean isDeleted);
}
