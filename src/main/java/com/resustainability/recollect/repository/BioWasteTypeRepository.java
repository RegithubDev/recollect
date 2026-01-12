package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IBioWasteTypeResponse;
import com.resustainability.recollect.entity.backend.BioWasteType;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BioWasteTypeRepository extends JpaRepository<BioWasteType, Long> {
	
	@Query("""
	        SELECT
	            bt.id AS id,
	            bt.biowasteName AS biowasteName,
	            bt.image AS image,
	            bt.isActive AS isActive,
	            bc.id AS categoryId,
	            bc.categoryName AS categoryName
	        FROM BioWasteType bt
	        JOIN bt.biowasteCategory bc
	        WHERE bc.id = :categoryId
	          AND (
	              :q IS NULL OR :q = '' OR
	              bt.biowasteName LIKE CONCAT(:q, '%')
	          )
	    """)
	    Page<IBioWasteTypeResponse> findAllPagedByCategory(
	            @Param("categoryId") Long categoryId,
	            @Param("q") String q,
	            Pageable pageable
	    );
	
	 @Query("""
		        SELECT
		            bt.id AS id,
		            bt.biowasteName AS biowasteName,
		            bt.image AS image,
		            bt.isActive AS isActive,
		            bc.id AS categoryId,
		            bc.categoryName AS categoryName
		        FROM BioWasteType bt
		        JOIN bt.biowasteCategory bc
		        WHERE bt.id = :id
		    """)
		    Optional<IBioWasteTypeResponse> findByBioWasteTypeId(@Param("id") Long id);
	 

	    @Modifying
	    @Transactional
	    @Query("""
	        UPDATE BioWasteType bt
	        SET bt.image = :image
	        WHERE bt.id = :id
	    """)
	    int updateImageById(
	            @Param("id") Long id,
	            @Param("image") String image
	    );
}
