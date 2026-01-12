package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.entity.backend.ServiceCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    @Query("""
        SELECT
            sc.id AS id,
            sc.serviceName AS name,
            sc.serviceTitle AS title,
            sc.serviceSubtitle AS subtitle,
            sc.icon AS icon,
            sc.isActive AS isActive,
            sc.isDisabled AS isDisabled,
            sc.categoryUrl AS categoryUrl,
            sc.orderUrl AS orderUrl
        FROM ServiceCategory sc
        WHERE sc.isActive = true
        AND sc.isDisabled = false
    """)
    List<IServiceCategoryResponse> findAllActiveServices();
    
    
    @Query("""
            SELECT
                sc.id AS id,
                sc.serviceName AS name,
                sc.serviceTitle AS title,
                sc.serviceSubtitle AS subtitle,
                sc.icon AS icon,
                sc.isActive AS isActive,
                sc.isDisabled AS isDisabled,
                sc.categoryUrl AS categoryUrl,
                sc.orderUrl AS orderUrl
            FROM ServiceCategory sc
            WHERE
                (:q IS NULL OR :q = '' OR
                    sc.serviceName LIKE CONCAT(:q, '%') OR
                    sc.serviceTitle LIKE CONCAT(:q, '%')
                )
        """)
        Page<IServiceCategoryResponse> findAllPaged(
                @Param("q") String searchTerm,
                Pageable pageable
        );

        @Query("""
            SELECT
                sc.id AS id,
                sc.serviceName AS name,
                sc.serviceTitle AS title,
                sc.serviceSubtitle AS subtitle,
                sc.icon AS icon,
                sc.isActive AS isActive,
                sc.isDisabled AS isDisabled,
                sc.categoryUrl AS categoryUrl,
                sc.orderUrl AS orderUrl
            FROM ServiceCategory sc
            WHERE sc.id = :id
        """)
        Optional<IServiceCategoryResponse> findByServiceCategoryId(
                @Param("id") Long id
        );

        boolean existsByServiceNameIgnoreCase(String serviceName);


        @Modifying(clearAutomatically = true)
        @Query("""
            UPDATE ServiceCategory sc
            SET sc.icon = :icon
            WHERE sc.id = :id
        """)
        int updateIconById(
                @Param("id") Long id,
                @Param("icon") String icon
        );


        @Modifying(clearAutomatically = true)
        @Query("""
            UPDATE ServiceCategory sc
            SET sc.isActive = :isActive,
                sc.isDisabled = :isDisabled
            WHERE sc.id = :id
        """)
        int softDelete(
                @Param("id") Long id,
                @Param("isActive") boolean isActive,
                @Param("isDisabled") boolean isDisabled
        );
}
