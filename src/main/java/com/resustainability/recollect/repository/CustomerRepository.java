package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.ICustomerResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.backend.Customer;

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
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("""
        SELECT
            c.id AS id,
            c.phoneNumber AS username,
            c.fullName AS fullName,
            c.phoneNumber AS phoneNumber,
            c.email AS email,
            c.userType AS userType,
            c.tokenAt AS tokenAt,
            c.isSuperuser AS isSuperuser,
            c.isStaff AS isStaff,
            c.isActive AS isActive,
            c.isDeleted AS isDeleted,
    
            true AS isCustomer,
            false AS isAdmin,
            false AS isProvider,
    
            c.district.id AS districtId,
            c.state.id AS stateId,
            c.scrapRegion.id AS scrapRegionId,
            c.ward.id AS wardId
        FROM Customer c
        WHERE c.phoneNumber = :username
    """)
    Optional<IUserContext> loadUserByUsername(@Param("username") String username);

    @Query("""
        SELECT
            c.id AS id,
            c.phoneNumber AS username,
            c.fullName AS fullName,
            c.phoneNumber AS phoneNumber,
            c.email AS email,
            c.userType AS userType,
            c.tokenAt AS tokenAt,
            c.isSuperuser AS isSuperuser,
            c.isStaff AS isStaff,
            c.isActive AS isActive,
            c.isDeleted AS isDeleted,
    
            true AS isCustomer,
            false AS isAdmin,
            false AS isProvider,
    
            c.district.id AS districtId,
            c.state.id AS stateId,
            c.scrapRegion.id AS scrapRegionId,
            c.ward.id AS wardId
        FROM Customer c
        WHERE c.id = :id
    """)
    Optional<IUserContext> loadUserById(@Param("id") Long id);

    @Query("""
        SELECT c
        FROM Customer c
        WHERE c.phoneNumber = :phone
    """)
    Optional<Customer> findByPhoneNumber(@Param("phone") String phoneNumber);

    @Query("""
        SELECT
        CASE WHEN COUNT(c) > 0
            THEN true
            ELSE false
        END
        FROM Customer c
        WHERE c.phoneNumber = :phone
    """)
    boolean existsByPhoneNumber(@Param("phone") String phoneNumber);

    @Query("""
        SELECT
            c.id AS id,
            c.lastLogin AS lastLogin,
            c.isSuperuser AS isSuperuser,
            c.isStaff AS isStaff,
            c.isActive AS isActive,
            c.dateJoined AS dateJoined,
            c.fullName AS fullName,
            c.otp AS otp,
            c.phoneNumber AS phoneNumber,
            c.email AS email,
            c.userType AS userType,
            c.platform AS platform,
            c.isDeleted AS isDeleted,
    
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
    
            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            r.id AS scrapRegionId,
            r.regionName AS scrapRegionName,
    
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
    
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName

        FROM Customer c
        LEFT JOIN c.district d
        LEFT JOIN d.state s
        LEFT JOIN c.scrapRegion r
        LEFT JOIN c.ward w
        LEFT JOIN w.localbody lb
        WHERE c.isDeleted = false
        AND
            (:searchTerm IS NULL OR :searchTerm = '' OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                c.phoneNumber LIKE CONCAT(:searchTerm, '%') OR
                c.email LIKE CONCAT(:searchTerm, '%') OR
                c.userType LIKE CONCAT(:searchTerm, '%') OR
                d.districtName LIKE CONCAT(:searchTerm, '%') OR
                s.stateName LIKE CONCAT(:searchTerm, '%') OR
                r.regionName LIKE CONCAT(:searchTerm, '%') OR
                w.wardName LIKE CONCAT(:searchTerm, '%') OR
                lb.localBodyName LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<ICustomerResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT
            c.id AS id,
            c.lastLogin AS lastLogin,
            c.isSuperuser AS isSuperuser,
            c.isStaff AS isStaff,
            c.isActive AS isActive,
            c.dateJoined AS dateJoined,
            c.fullName AS fullName,
            c.otp AS otp,
            c.phoneNumber AS phoneNumber,
            c.email AS email,
            c.userType AS userType,
            c.platform AS platform,
            c.isDeleted AS isDeleted,
    
            d.id AS districtId,
            d.districtName AS districtName,
            d.districtCode AS districtCode,
    
            s.id AS stateId,
            s.stateName AS stateName,
            s.stateCode AS stateCode,
    
            r.id AS scrapRegionId,
            r.regionName AS scrapRegionName,
    
            w.id AS wardId,
            w.wardNo AS wardNo,
            w.wardName AS wardName,
    
            lb.id AS localBodyId,
            lb.localBodyName AS localBodyName

        FROM Customer c
        LEFT JOIN c.district d
        LEFT JOIN d.state s
        LEFT JOIN c.scrapRegion r
        LEFT JOIN c.ward w
        LEFT JOIN w.localbody lb
    
        WHERE
            c.id = :id
    """)
    Optional<ICustomerResponse> findByCustomerId(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Customer c
        SET c.tokenAt = :tokenAt
        WHERE c.id = :id
    """)
    int updateTokenAtById(
            @Param("id") Long id,
            @Param("tokenAt") LocalDateTime tokenAt
    );

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Customer c
        SET c.lastLogin = :lastLogin
        WHERE c.id = :id
    """)
    int updateLastLoginAtById(
            @Param("id") Long id,
            @Param("lastLogin") LocalDateTime lastLogin
    );
}
