package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.backend.Provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query("""
        SELECT
            p.id AS id,
            p.phoneNumber AS username,
            p.fullName AS fullName,
            p.phoneNumber AS phoneNumber,
            p.email AS email,
            p.tokenAt AS tokenAt,
            p.isSuperuser AS isSuperuser,
            p.isStaff AS isStaff,
            p.isActive AS isActive,
            p.isDeleted AS isDeleted,
    
            false AS isCustomer,
            false AS isAdmin,
            true AS isProvider,
    
            p.state.id AS stateId,
    
            r.id AS roleId,
            r.roleName AS roleName,
            r.isActive AS roleActive
        FROM Provider p
        LEFT JOIN p.role r
        WHERE p.phoneNumber = :username
    """)
    Optional<IUserContext> loadUserByUsername(@Param("username") String username);

    @Query("""
        SELECT
            p.id AS id,
            p.phoneNumber AS username,
            p.fullName AS fullName,
            p.phoneNumber AS phoneNumber,
            p.email AS email,
            p.tokenAt AS tokenAt,
            p.isSuperuser AS isSuperuser,
            p.isStaff AS isStaff,
            p.isActive AS isActive,
            p.isDeleted AS isDeleted,
    
            false AS isCustomer,
            false AS isAdmin,
            true AS isProvider,
    
            p.state.id AS stateId,
    
            r.id AS roleId,
            r.roleName AS roleName,
            r.isActive AS roleActive
        FROM Provider p
        LEFT JOIN p.role r
        WHERE p.id = :id
    """)
    Optional<IUserContext> loadUserById(@Param("id") Long id);

    @Query("""
        SELECT p
        FROM Provider p
        WHERE p.phoneNumber = :username
        AND p.plainPassword = :password
    """)
    Optional<Provider> findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

    @Query("""
        SELECT
            p.id AS id,
            p.providerCode AS code,
            p.fullName AS fullName,
            p.phoneNumber AS phoneNumber,

            p.scrapCashInHand AS scrapCashInHand,
            p.bioCashInHand AS bioCashInHand,
            p.bwgScrapCashInHand AS bwgScrapCashInHand,
            p.bwgBioCashInHand AS bwgBioCashInHand,
            p.totalCashInHand AS totalCashInHand,

            p.plainPassword AS password,

            p.scrapPickup AS scrapPickup,
            p.biowastePickup AS biowastePickup,
            p.bwgBioPickup AS bwgBioPickup,
            p.bwgScrapPickup AS bwgScrapPickup,
            p.orderPickupLimit AS orderPickupLimit,

            p.isActive AS isActive,
            p.isDeleted AS isDeleted,

            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode

        FROM Provider p
        LEFT JOIN p.state s
        WHERE p.isDeleted = false
        AND
            (:searchTerm IS NULL OR :searchTerm = '' OR
                p.providerCode LIKE CONCAT(:searchTerm, '%') OR
                p.fullName LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IProviderResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            p.id AS id,
            p.providerCode AS code,
            p.fullName AS fullName,
            p.scrapCashInHand AS scrapCashInHand,
            p.bioCashInHand AS bioCashInHand,
            p.bwgScrapCashInHand AS bwgScrapCashInHand,
            p.bwgBioCashInHand AS bwgBioCashInHand,
            p.totalCashInHand AS totalCashInHand

        FROM Provider p
        LEFT JOIN p.state s
        WHERE p.isDeleted = false
        AND
            (:searchTerm IS NULL OR :searchTerm = '' OR
                p.providerCode LIKE CONCAT(:searchTerm, '%') OR
                p.fullName LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IProviderResponse> findAllCashCollectionPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Provider p
        SET p.tokenAt = :tokenAt
        WHERE p.id = :id
    """)
    int updateTokenAtById(
            @Param("id") Long id,
            @Param("tokenAt") LocalDateTime tokenAt
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Provider p
        SET p.lastLogin = :lastLogin
        WHERE p.id = :id
    """)
    int updateLastLoginAtById(
            @Param("id") Long id,
            @Param("lastLogin") LocalDateTime lastLogin
    );
}
