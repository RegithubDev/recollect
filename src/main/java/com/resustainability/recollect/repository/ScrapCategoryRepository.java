package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IScrapCategoryResponse;
import com.resustainability.recollect.dto.response.IScrapCategoryTypeResponse;
import com.resustainability.recollect.entity.backend.ScrapCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapCategoryRepository extends JpaRepository<ScrapCategory, Long> {
    @Query(nativeQuery = true, value = """
        SELECT
            c.id AS categoryId,
            c.category_name AS categoryName,
            c.subcategory_name AS subcategoryName,
            c.image AS categoryIcon,

            t.id AS typeId,
            t.scrap_name AS typeName,
            t.image AS typeIcon,
            t.is_payable AS typeIsPayable,
            t.is_kg AS typeIsKg,

            p.scrap_price AS price,
            p.scrap_cgst AS cgst,
            p.scrap_sgst AS sgst
        FROM backend_scrapcategory c
        JOIN backend_scraptype t
            ON t.scrap_category_id = c.id
        JOIN backend_scraptypelocationandprice p
            ON p.scrap_type_id = t.id
           AND p.district_id = :districtId
           AND p.is_active = 1
        WHERE c.is_active = 1
          AND t.is_active = 1
        ORDER BY c.id, t.id;
    """)
    List<IScrapCategoryTypeResponse> findAllActiveCategoryTypes(
            @Param("districtId") Long districtId
    );
    
    
   /* @Query("""
    	    SELECT
    	        sc.id AS id,
    	        sc.categoryName AS categoryName,
    	        sc.subcategoryName AS subcategoryName,
    	        sc.image AS image,
    	        sc.isActive AS isActive,
    	        sc.hsnCode AS hsnCode
    	    FROM ScrapCategory sc
    	    WHERE
    	        (:q IS NULL OR :q = '' OR
    	            sc.categoryName LIKE CONCAT(:q, '%') OR
    	            sc.subcategoryName LIKE CONCAT(:q, '%')
    	        )
    	""")
    	Page<IScrapCategoryResponse> findAllPaged(
    	        @Param("q") String q,
    	        Pageable pageable
    	);*/
    
    @Query("""
    	    SELECT
    	        sc.id AS id,
    	        sc.categoryName AS categoryName,
    	        sc.subcategoryName AS subcategoryName,
    	        sc.image AS image,
    	        sc.isActive AS isActive,
    	        sc.hsnCode AS hsnCode
    	    FROM ScrapCategory sc
    	    WHERE
    	        sc.subcategoryName = :subcategory
    	    AND (
    	        :q IS NULL OR :q = '' OR
    	        sc.categoryName LIKE CONCAT(:q, '%') OR
    	        sc.subcategoryName LIKE CONCAT(:q, '%')
    	    )
    	""")
    	Page<IScrapCategoryResponse> findAllPagedBySubcategory(
    	        @Param("subcategory") String subcategory,
    	        @Param("q") String q,
    	        Pageable pageable
    	);



    @Query("""
            SELECT
               sc.id AS id,
    	        sc.categoryName AS categoryName,
    	        sc.subcategoryName AS subcategoryName,
    	        sc.image AS image,
    	        sc.isActive AS isActive,
    	        sc.hsnCode AS hsnCode
    	    FROM ScrapCategory sc
            WHERE sc.id = :id
        """)
        Optional<IScrapCategoryResponse> findByScrapCategoryId(
                @Param("id") Long id
        );

        @Modifying
        @Transactional
        @Query("""
            UPDATE ScrapCategory sc
            SET sc.image = :image
            WHERE sc.id = :id
        """)
        int updateImageById(
                @Param("id") Long id,
                @Param("image") String image
        );


        @Modifying
        @Transactional
        @Query("""
            UPDATE ScrapCategory sc
            SET sc.isActive = false
            WHERE sc.id = :id
        """)
        int softDelete(@Param("id") Long id);
    

}
