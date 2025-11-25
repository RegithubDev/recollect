package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderRoleResponse;
import com.resustainability.recollect.entity.backend.ProviderRoles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

@Repository
public interface ProviderRoleRepository extends JpaRepository<ProviderRoles, Long> {

    @Query("""
        SELECT
            pr.id AS id,
            pr.roleName AS roleName,
            pr.isAdmin AS isAdmin,
            pr.isActive AS isActive
        FROM ProviderRoles pr
        WHERE (:search IS NULL OR :search = '' OR pr.roleName LIKE CONCAT(:search, '%'))
    """)
    Page<IProviderRoleResponse> findAllPaged(
            @Param("search") String search,
            Pageable pageable
    );

    @Query("""
        SELECT
            pr.id AS id,
            pr.roleName AS roleName,
            pr.isAdmin AS isAdmin,
            pr.isActive AS isActive
        FROM ProviderRoles pr
        WHERE pr.id = :id
    """)
    Optional<IProviderRoleResponse> findByProviderRoleId(@Param("id") Long id);

    boolean existsByRoleName(String roleName);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE ProviderRoles pr
        SET pr.isActive = :isActive
        WHERE pr.id = :id
    """)
    int updateIsActive(@Param("id") Long id, @Param("isActive") Boolean isActive);
}
