package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IBioWasteCategoryTypeResponse;
import com.resustainability.recollect.dto.response.IBwgOrderCartResponse;
import com.resustainability.recollect.dto.response.ItemCategoryTypeResponse;
import com.resustainability.recollect.entity.backend.BwgOrderCart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BwgOrderCartRepository extends JpaRepository<BwgOrderCart, Long> {
	@Query("""
        SELECT
            COALESCE(bt.id, st.id) AS id,
            COALESCE(bt.biowasteName, st.scrapName) AS name
        FROM BwgOrderCart c
        JOIN c.bwgOrder o
        LEFT JOIN c.scrapType st
    	LEFT JOIN c.bioWasteType bt
        WHERE c.isDeleted = false
        AND o.id = :orderId
    """)
	List<ItemCategoryTypeResponse> findAllTypesByOrderId(
			@Param("orderId") Long orderId
	);

    @Query("""
        SELECT
            c.id AS id,
            o.id AS orderId,
           
            st.id AS scrapTypeId,
            st.scrapName AS scrapName,
            
           bt.id AS bioWasteTypeId,
    	   bt.biowasteName AS biowasteName,
            
            c.scrapWeight AS scrapWeight,
            c.scrapPrice AS scrapPrice,
            c.scrapGst AS scrapGst,
            c.scrapHsn AS scrapHsn,
            c.totalPrice AS totalPrice
        FROM BwgOrderCart c
        JOIN c.bwgOrder o
        LEFT JOIN c.scrapType st
    	LEFT JOIN c.bioWasteType bt
        WHERE c.isDeleted = false
        AND o.id = :orderId
    """)
    Page<IBwgOrderCartResponse> findAllResponsesByOrderId(
            Long orderId,
            Pageable pageable
    );

    @Query("""
    	    SELECT
    	        c.id AS id,
    	        o.id AS orderId,

    	        st.id AS scrapTypeId,
    	        st.scrapName AS scrapName,

    	        bt.id AS bioWasteTypeId,
    	        bt.biowasteName AS biowasteName,

    	        c.scrapWeight AS scrapWeight,
    	        c.scrapPrice AS scrapPrice,
    	        c.scrapGst AS scrapGst,
    	        c.scrapHsn AS scrapHsn,
    	        c.totalPrice AS totalPrice
    	    FROM BwgOrderCart c
    	    JOIN c.bwgOrder o
    	    LEFT JOIN c.scrapType st
    	    LEFT JOIN c.bioWasteType bt
    	    WHERE c.isDeleted = false
    	    AND c.id = :id
    	""")
    	Optional<IBwgOrderCartResponse> findResponseById(@Param("id") Long id);


    @Modifying
    @Query("""
        UPDATE BwgOrderCart c
        SET c.isDeleted = :isDeleted
        WHERE c.id = :id
    """)
    int softDelete(Long id, boolean isDeleted);
    
    @Query("""
            SELECT c
            FROM BwgOrderCart c
            WHERE c.isDeleted = false
            AND c.id = :id
        """)
        Optional<BwgOrderCart> findActiveEntityById(Long id);
}
