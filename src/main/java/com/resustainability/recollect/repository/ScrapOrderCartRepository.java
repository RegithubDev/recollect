package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.ScrapOrderCart;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapOrderCartRepository extends JpaRepository<ScrapOrderCart, Long> {
	@Query(value = """
        SELECT sc
        FROM ScrapOrderCart sc
        JOIN sc.scrapOrder so
        WHERE so.id = :id
            AND sc.isDeleted = false
    """)
    List<ScrapOrderCart> findByScrapOrderId(@Param("id") Long scrapOrderId);
}
