package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IScrapCategoryTypeResponse;
import com.resustainability.recollect.entity.backend.ScrapCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        FROM Backend_scrapcategory c
        JOIN Backend_scraptype t
            ON t.scrap_category_id = c.id
        JOIN Backend_scraptypelocationandprice p
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
}
