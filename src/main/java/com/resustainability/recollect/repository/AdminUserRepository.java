package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IAdminUserResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.backend.AdminUser;

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
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    @Query("""
        SELECT
            u.id AS id,
            u.username AS username,
            u.fullName AS fullName,
            u.phoneNumber AS phoneNumber,
            u.email AS email,
            u.tokenAt AS tokenAt,
            u.isSuperuser AS isSuperuser,
            u.isStaff AS isStaff,
            u.isActive AS isActive,
            u.isDeleted AS isDeleted,
    
            false AS isCustomer,
            true AS isAdmin,
            false AS isProvider,
    
            r.id AS roleId,
            r.roleName AS roleName,
            r.isActive AS roleActive
        FROM AdminUser u
        LEFT JOIN u.adminRole r
        WHERE u.username = :username
    """)
    Optional<IUserContext> loadUserByUsername(@Param("username") String username);

    @Query("""
        SELECT
            u.id AS id,
            u.username AS username,
            u.fullName AS fullName,
            u.phoneNumber AS phoneNumber,
            u.email AS email,
            u.tokenAt AS tokenAt,
            u.isSuperuser AS isSuperuser,
            u.isStaff AS isStaff,
            u.isActive AS isActive,
            u.isDeleted AS isDeleted,
    
            false AS isCustomer,
            true AS isAdmin,
            false AS isProvider,
 
            r.id AS roleId,
            r.roleName AS roleName,
            r.isActive AS roleActive
        FROM AdminUser u
        LEFT JOIN u.adminRole r
        WHERE u.id = :id
    """)
    Optional<IUserContext> loadUserById(@Param("id") Long id);

    @Query("""
        SELECT u
        FROM AdminUser u
        WHERE u.username = :username
        AND u.plainPassword = :password
    """)
    Optional<AdminUser> findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

    @Query("""
        SELECT
            u.id AS id,
            u.username AS username,
            u.fullName AS fullName,
            u.phoneNumber AS phoneNumber,
            u.email AS email,
            u.isSuperuser AS isSuperuser,
            u.isStaff AS isStaff,
            u.isActive AS isActive,
            u.isDeleted AS isDeleted,
            u.lastLogin AS lastLogin,
            u.dateJoined AS dateJoined,
            r.id AS roleId,
            r.roleName AS roleName,
            r.isActive AS roleActive
        FROM AdminUser u
        LEFT JOIN u.adminRole r
        WHERE
            (:searchTerm IS NULL OR :searchTerm = '' OR
                u.username LIKE CONCAT(:searchTerm, '%') OR
                u.fullName LIKE CONCAT(:searchTerm, '%') OR
                u.phoneNumber LIKE CONCAT(:searchTerm, '%') OR
                u.email LIKE CONCAT(:searchTerm, '%') OR
                r.roleName LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<IAdminUserResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            u.id AS id,
            u.username AS username,
            u.fullName AS fullName,
            u.phoneNumber AS phoneNumber,
            u.email AS email,
            u.isSuperuser AS isSuperuser,
            u.isStaff AS isStaff,
            u.isActive AS isActive,
            u.isDeleted AS isDeleted,
            u.lastLogin AS lastLogin,
            u.dateJoined AS dateJoined,
            r.id AS roleId,
            r.roleName AS roleName,
            r.isActive AS roleActive
        FROM AdminUser u
        LEFT JOIN u.adminRole r
        WHERE u.id = :adminUserId
    """)
    Optional<IAdminUserResponse> findByAdminUserId(@Param("adminUserId") Long adminUserId);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE AdminUser u
        SET u.tokenAt = :tokenAt
        WHERE u.id = :id
    """)
    int updateTokenAtById(
            @Param("id") Long id,
            @Param("tokenAt") LocalDateTime tokenAt
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE AdminUser u
        SET u.lastLogin = :lastLogin
        WHERE u.id = :id
    """)
    int updateLastLoginAtById(
            @Param("id") Long id,
            @Param("lastLogin") LocalDateTime lastLogin
    );
}
