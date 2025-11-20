package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IStateResponse;
import com.resustainability.recollect.entity.backend.State;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	@Query("""
		    SELECT 
		        s.id AS id,
		        s.stateName AS name,
		        s.stateCode AS code,
		        s.stateImage AS icon,
		        s.isActive AS isActive,
		        s.isDeleted AS isDeleted,
		        s.country.id AS countryId
		    FROM State s
		    WHERE s.isDeleted = false
		    AND (:countryId IS NULL OR s.country.id = :countryId)
		    AND (:searchTerm IS NULL OR :searchTerm = '' OR
		            s.stateName LIKE CONCAT(:searchTerm, '%') OR
		            s.stateCode LIKE CONCAT(:searchTerm, '%')
		    )
		""")
		Page<IStateResponse> findAllPaged(
		        @Param("searchTerm") String searchTerm,
		        @Param("countryId") Long countryId,
		        Pageable pageable
		);



    @Query("""
        SELECT
            s.id AS id,
            s.stateName AS name,
            s.stateCode AS code,
            s.stateImage AS icon,
            s.isActive AS isActive,
            s.isDeleted AS isDeleted,
            s.country.id AS countryId,
            s.country.countryName AS countryName
        FROM State s
        WHERE s.id = :id
    """)
    Optional<IStateResponse> findByStateId(@Param("id") Long stateId);


    @Query("""
        SELECT 
        CASE WHEN COUNT(s) > 0 THEN true ELSE false END
        FROM State s
        WHERE s.stateCode = :code
    """)
    boolean existsByCode(@Param("code") String code);


    @Query("""
        SELECT s.stateImage
        FROM State s
        WHERE s.id = :id
    """)
    Optional<String> findStateImageById(@Param("id") Long stateId);


    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE State s
        SET s.stateImage = :filePath
        WHERE s.id = :id
    """)
    int updateStateImageById(
            @Param("id") Long stateId,
            @Param("filePath") String filePath
    );


    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE State s
        SET s.isActive = false,
            s.isDeleted = true
        WHERE s.id = :id
    """)
    int deleteStateById(@Param("id") Long stateId);

}
