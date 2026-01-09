package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IGeometryResponse;
import com.resustainability.recollect.dto.response.ILocalBodyResponse;
import com.resustainability.recollect.dto.response.ILocalBodyResponseByDistrictId;
import com.resustainability.recollect.dto.response.IWardResponse;
import com.resustainability.recollect.entity.backend.LocalBody;

import org.locationtech.jts.geom.MultiPolygon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface LocalBodyRepository extends JpaRepository<LocalBody, Long> {

    @Query("""
            SELECT 
                lb.id AS id,
                lb.localBodyName AS localBodyName,
                lb.isActive AS isActive,
                lb.isDeleted AS isDeleted,
                lb.bioProcessingCharge AS bioProcessingCharge,
                lb.bioServiceCharge AS bioServiceCharge,
                lb.bioSubsidyAmount AS bioSubsidyAmount,
                lb.bioCgstPercentage AS bioCgstPercentage,
                lb.bioSgstPercentage AS bioSgstPercentage,
                lb.bioResidentialPrice AS bioResidentialPrice,
                lb.bioCommercialPrice AS bioCommercialPrice,
                lb.isInclusiveCommercial AS isInclusiveCommercial,
                lb.isInclusiveResidential AS isInclusiveResidential,

                d.id AS districtId,
                d.districtName AS districtName,

                lbt.id AS localBodyTypeId,
                lbt.localBodyType AS localBodyTypeName,

                s.id AS stateId,
                s.stateName AS stateName,

                c.id AS countryId,
                c.countryName AS countryName

            FROM LocalBody lb
            JOIN lb.district d
            JOIN d.state s
            JOIN s.country c
            LEFT JOIN lb.localBodyType lbt

            WHERE lb.isDeleted = false
              AND (:districtId IS NULL OR d.id = :districtId)
              AND (:stateId IS NULL OR s.id = :stateId)
              AND (:countryId IS NULL OR c.id = :countryId)
              AND (:searchTerm IS NULL 
                   OR LOWER(lb.localBodyName) LIKE CONCAT(LOWER(:searchTerm), '%'))
            """)
    Page<ILocalBodyResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            @Param("districtId") Long districtId,
            @Param("stateId") Long stateId,
            @Param("countryId") Long countryId,
            Pageable pageable
    );

    @Query("""
    	    SELECT 
    	        lb.id AS id,
    	        lb.localBodyName AS localBodyName,
    	        lb.bioProcessingCharge AS bioProcessingCharge,
    	        lb.bioServiceCharge AS bioServiceCharge,
    	        lb.bioSubsidyAmount AS bioSubsidyAmount,
    	        lb.bioCgstPercentage AS bioCgstPercentage,
    	        lb.bioSgstPercentage AS bioSgstPercentage,
    	        lb.bioResidentialPrice AS bioResidentialPrice,
    	        lb.bioCommercialPrice AS bioCommercialPrice

    	    FROM LocalBody lb
    	    JOIN lb.district d

    	    WHERE lb.isDeleted = false
    	      AND d.id = :districtId
    	      AND (
    	            :searchTerm IS NULL
    	            OR LOWER(lb.localBodyName) LIKE CONCAT(LOWER(:searchTerm), '%')
    	          )
    	""")
    	Page<ILocalBodyResponseByDistrictId> findAllByDistrictPaged(
    	        @Param("districtId") Long districtId,
    	        @Param("searchTerm") String searchTerm,
    	        Pageable pageable
    	);




    @Query("""
            SELECT 
                lb.id AS id,
                lb.localBodyName AS localBodyName,
                lb.isActive AS isActive,
                lb.isDeleted AS isDeleted,
                lb.bioProcessingCharge AS bioProcessingCharge,
                lb.bioServiceCharge AS bioServiceCharge,
                lb.bioSubsidyAmount AS bioSubsidyAmount,
                lb.bioCgstPercentage AS bioCgstPercentage,
                lb.bioSgstPercentage AS bioSgstPercentage,
                lb.bioResidentialPrice AS bioResidentialPrice,
                lb.bioCommercialPrice AS bioCommercialPrice,
                lb.isInclusiveCommercial AS isInclusiveCommercial,
                lb.isInclusiveResidential AS isInclusiveResidential,

                d.id AS districtId,
                d.districtName AS districtName,

                lbt.id AS localBodyTypeId,
                lbt.localBodyType AS localBodyTypeName,

                s.id AS stateId,
                s.stateName AS stateName,

                c.id AS countryId,
                c.countryName AS countryName

            FROM LocalBody lb
            JOIN lb.district d
            JOIN d.state s
            JOIN s.country c
            LEFT JOIN lb.localBodyType lbt

            WHERE lb.id = :id
            """)
    Optional<ILocalBodyResponse> findByLocalBodyId(@Param("id") Long id);


    boolean existsByLocalBodyName(String localBodyName);

	@Query("""
        SELECT
           lb.id AS id,
           lb.geometry AS geometry
        FROM LocalBody lb
        WHERE lb.id = :localBodyId
    """)
	Optional<IGeometryResponse> findBorderByLocalBodyId(@Param("localBodyId") Long localBodyId);

	@Query(nativeQuery = true, value = """
		SELECT
			w.id AS id,
			w.ward_no AS wardNo,
			w.ward_name AS wardName,
	
			lb.id AS localBodyId,
			lb.localbody_name AS localBodyName,
	
			d.id AS districtId,
			d.district_name AS districtName,
	
			s.id AS stateId,
			s.state_name AS stateName,
	
			c.id AS countryId,
			c.country_name AS countryName
		FROM backend_ward w
		INNER JOIN backend_localbody lb
			ON lb.id = w.localbody_id
		INNER JOIN backend_district d
			ON d.id = lb.district_id
		INNER JOIN backend_state s
			ON s.id = d.state_id
		INNER JOIN backend_country c
			ON c.id = s.country_id
		WHERE ST_Contains(
			lb.geometry,
			ST_SRID(POINT(:lon, :lat), :srid)
		)
		  AND w.is_active = true
		  AND w.is_deleted = false
		  AND lb.is_active = true
		  AND lb.is_deleted = false
	""")
	List<IWardResponse> findWardsContainingGeometry(
			@Param("lat") double lat,
			@Param("lon") double lon,
			@Param("srid") int srid
	);

	@Query("""
        SELECT lb.geometry
        FROM LocalBody lb
    """)
	Stream<MultiPolygon> streamAllActiveGeometries();

	@Modifying(clearAutomatically = true)
	@Query("""
        UPDATE LocalBody lb
        SET lb.isActive =
                CASE
                    WHEN lb.isActive = true THEN false
                    ELSE true
                END
        WHERE lb.id = :localBodyId
    """)
	int toggleActiveStatusById(@Param("localBodyId") Long localBodyId);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE LocalBody lb
            SET lb.isActive = :isActive,
                lb.isDeleted = :isDeleted
            WHERE lb.id = :id
            """)
    int deleteLocalBodyById(
            @Param("id") Long id,
            @Param("isActive") boolean isActive,
            @Param("isDeleted") boolean isDeleted
    );


	@Query(nativeQuery = true, value = """
        SELECT EXISTS (
            SELECT 1
            FROM backend_localbody lb
            WHERE lb.id = :localBodyId
              AND ST_Contains(
                  lb.geometry,
                  ST_SRID(POINT(:lon, :lat), :srid)
              )
        )
    """)
	Long existsContainingGeometryById(
			@Param("localBodyId") Long localBodyId,
			@Param("lat") double lat,
			@Param("lon") double lon,
			@Param("srid") int srid
	);
}
