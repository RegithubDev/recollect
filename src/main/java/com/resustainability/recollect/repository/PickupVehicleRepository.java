package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IPickupVehicleResponse;
import com.resustainability.recollect.entity.backend.PickupVehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PickupVehicleRepository extends JpaRepository<PickupVehicle, Long> {

    @Query("""
        SELECT 
            pv.id AS id,
            pv.vehicleName AS vehicleName,
            pv.vehicleNumber AS vehicleNumber,
            pv.pickupType AS pickupType,
            pv.vehicleImage AS vehicleImage,
            pv.isAssigned AS isAssigned,
            pv.isActive AS isActive,
            pv.isDeleted AS isDeleted,
            s.id AS stateId,
            s.stateName AS stateName
        FROM PickupVehicle pv
        LEFT JOIN pv.state s
        WHERE pv.isDeleted = false
        AND (:stateId IS NULL OR s.id = :stateId)
        AND (:searchTerm IS NULL OR :searchTerm = '' OR 
                pv.vehicleName LIKE CONCAT(:searchTerm, '%') OR
                pv.vehicleNumber LIKE CONCAT(:searchTerm, '%')
        )
    """)
    Page<IPickupVehicleResponse> findAllPaged(
            @Param("searchTerm") String searchTerm,
            @Param("stateId") Long stateId,
            Pageable pageable
    );


    @Query("""
        SELECT 
            pv.id AS id,
            pv.vehicleName AS vehicleName,
            pv.vehicleNumber AS vehicleNumber,
            pv.pickupType AS pickupType,
            pv.vehicleImage AS vehicleImage,
            pv.isAssigned AS isAssigned,
            pv.isActive AS isActive,
            pv.isDeleted AS isDeleted,
            s.id AS stateId,
            s.stateName AS stateName
        FROM PickupVehicle pv
        LEFT JOIN pv.state s
        WHERE pv.id = :id
    """)
    Optional<IPickupVehicleResponse> findByPickupVehicleId(@Param("id") Long id);


    @Query("""
        SELECT 
        CASE WHEN COUNT(pv) > 0 THEN true ELSE false END
        FROM PickupVehicle pv
        WHERE pv.vehicleNumber = :vehicleNumber
    """)
    boolean existsByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);


    @Query("""
        SELECT pv.vehicleImage
        FROM PickupVehicle pv
        WHERE pv.id = :id
    """)
    Optional<String> findVehicleImageById(@Param("id") Long id);


    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE PickupVehicle pv
        SET pv.vehicleImage = :filePath
        WHERE pv.id = :id
    """)
    int updateVehicleImageById(
            @Param("id") Long id,
            @Param("filePath") String filePath
    );


    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE PickupVehicle pv
        SET pv.isActive = :isActive,
            pv.isDeleted = :isDeleted
        WHERE pv.id = :id
    """)
    int deletePickupVehicleById(
            @Param("id") Long id,
            @Param("isActive") boolean isActive,
            @Param("isDeleted") boolean isDeleted
    );

}
