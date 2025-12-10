package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IBioWasteCategoryTypeResponse;
import com.resustainability.recollect.entity.backend.BioWasteCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BioWasteCategoryRepository extends JpaRepository<BioWasteCategory, Long> {
    @Query(nativeQuery = true, value = """
        SELECT
            c.id AS categoryId,
            c.category_name AS categoryName,
            c.image AS categoryIcon,
            t.id AS typeId,
            t.biowaste_name AS typeName,
            t.image AS typeIcon
        FROM Backend_biowastecategory c
        JOIN Backend_biowastetype t
            ON t.biowaste_category_id = c.id
        WHERE c.is_active = 1 AND t.is_active = 1
        ORDER BY c.id, t.id
    """)
    List<IBioWasteCategoryTypeResponse> findAllActiveCategoryTypes();
}
