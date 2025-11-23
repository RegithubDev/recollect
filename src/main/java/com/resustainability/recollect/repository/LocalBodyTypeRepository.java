package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.ILocalBodyTypeResponse;
import com.resustainability.recollect.entity.backend.LocalBodyType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalBodyTypeRepository extends JpaRepository<LocalBodyType, Long> {

    @Query("""
            SELECT 
                l.id AS id,
                l.localBodyType AS name,
                l.isActive AS isActive,
                l.isDeleted AS isDeleted
            FROM LocalBodyType l
            WHERE l.isDeleted = false
            AND (
                :searchTerm IS NULL OR :searchTerm = '' 
                OR l.localBodyType LIKE CONCAT(:searchTerm, '%')
            )
        """)
    Page<ILocalBodyTypeResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );


    @Query("""
            SELECT 
                l.id AS id,
                l.localBodyType AS name,
                l.isActive AS isActive,
                l.isDeleted AS isDeleted
            FROM LocalBodyType l
            WHERE l.id = :id
        """)
    Optional<ILocalBodyTypeResponse> findByLocalBodyTypeId(@Param("id") Long id);


    @Query("""
            SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
            FROM LocalBodyType l
            WHERE l.localBodyType = :name
        """)
    boolean existsByName(@Param("name") String name);


    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE LocalBodyType l
            SET l.isActive = :isActive,
                l.isDeleted = :isDeleted
            WHERE l.id = :id
        """)
    int deleteLocalBodyTypeById(
            @Param("id") Long id,
            @Param("isActive") boolean isActive,
            @Param("isDeleted") boolean isDeleted
    );
}
