package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.entity.backend.ServiceCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            sc.isDisabled AS isDisabled
        FROM ServiceCategory sc
        WHERE sc.isActive = true
        AND sc.isDisabled = false
    """)
    List<IServiceCategoryResponse> findAllActiveServices();
}
