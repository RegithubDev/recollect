package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IScrapTypeResponse;
import com.resustainability.recollect.entity.backend.ScrapType;

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
public interface ScrapTypeRepository extends JpaRepository<ScrapType, Long> {
	
	Optional<ScrapType> findByIdAndIsActiveTrue(Long id);
	
	

 @Query("""
     SELECT
         st.id AS id,
         st.scrapName AS scrapName,
         st.image AS image,
         st.isPayable AS isPayable,
         st.isKg AS isKg,
         st.isActive AS isActive,
         sc.id AS categoryId,
         sc.categoryName AS categoryName
     FROM ScrapType st
     JOIN st.scrapCategory sc
     WHERE sc.id = :categoryId
     AND (
         :q IS NULL OR :q = '' OR
         st.scrapName LIKE CONCAT(:q, '%')
     )
 """)
 Page<IScrapTypeResponse> findAllPagedByCategory(
         @Param("categoryId") Long categoryId,
         @Param("q") String q,
         Pageable pageable
 );

 @Query("""
     SELECT
         st.id AS id,
         st.scrapName AS scrapName,
         st.image AS image,
         st.isPayable AS isPayable,
         st.isKg AS isKg,
         st.isActive AS isActive,
         sc.id AS categoryId,
         sc.categoryName AS categoryName
     FROM ScrapType st
     JOIN st.scrapCategory sc
     WHERE st.id = :id
 """)
 Optional<IScrapTypeResponse> findByScrapTypeId(
         @Param("id") Long id
 );

 @Modifying
 @Transactional
 @Query("""
     UPDATE ScrapType st
     SET st.image = :image
     WHERE st.id = :id
 """)
 int updateImageById(
         @Param("id") Long id,
         @Param("image") String image
 );

}
