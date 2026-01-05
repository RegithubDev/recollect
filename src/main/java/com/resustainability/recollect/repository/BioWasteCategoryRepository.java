package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IBioWasteCategoryTypeResponse;
import com.resustainability.recollect.entity.backend.BioWasteCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            t.image AS typeIcon,
            lb.bio_residential_price AS residentialPrice,
            lb.bio_commercial_price AS commercialPrice
        FROM backend_biowastecategory c
        JOIN backend_biowastetype t
            ON t.biowaste_category_id = c.id
        LEFT JOIN backend_localbody lb
            ON lb.id = :localBodyId
        WHERE c.is_active = 1 AND t.is_active = 1
        ORDER BY c.id, t.id
    """)
    List<IBioWasteCategoryTypeResponse> findAllActiveCategoryTypes(
            @Param("localBodyId") Long localBodyId
    );
}
