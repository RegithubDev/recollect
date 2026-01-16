package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.BioWasteOrderCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BioWasteOrderCartRepository extends JpaRepository<BioWasteOrderCart, Long> {
    @Query(value = """
        SELECT bc
        FROM BioWasteOrderCart bc
        JOIN bc.biowasteOrder bo
        WHERE bo.id = :id
            AND bc.isDeleted = false
    """)
    List<BioWasteOrderCart> findByBioWasteOrderId(@Param("id") Long bioWasteOrderId);
}
