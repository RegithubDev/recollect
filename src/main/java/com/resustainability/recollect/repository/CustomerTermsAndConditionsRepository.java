package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.ICustomerTermsAndConditionsResponse;
import com.resustainability.recollect.entity.backend.CustomerTermsAndConditions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CustomerTermsAndConditionsRepository extends JpaRepository<CustomerTermsAndConditions, Long> {
    @Query(value = """
        SELECT
            tc.id AS id,
            tc.optional AS isOptional,
            tc.signedDate AS signedDate,
            c.id AS customerId,
            c.fullName AS fullName,
            c.phoneNumber AS phoneNumber
        FROM CustomerTermsAndConditions tc
        JOIN tc.customer c
        WHERE (
                :fromDate IS NULL OR tc.signedDate >= :fromDate AND
                :toDate IS NULL OR tc.signedDate <= :toDate
            )
            AND (:searchTerm IS NULL OR :searchTerm = '' OR
                c.fullName LIKE CONCAT(:searchTerm, '%') OR
                c.phoneNumber LIKE CONCAT(:searchTerm, '%')
            )
    """)
    Page<ICustomerTermsAndConditionsResponse> findAllPaged(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query(value = """
        SELECT
            tc.id AS id,
            tc.optional AS isOptional,
            tc.signedDate AS signedDate,
            c.id AS customerId,
            c.fullName AS fullName,
            c.phoneNumber AS phoneNumber
        FROM CustomerTermsAndConditions tc
        JOIN tc.customer c
        WHERE c.id = :id
    """)
    Optional<ICustomerTermsAndConditionsResponse> findByCustomerId(
            @Param("id") Long customerId
    );
}
