package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IPickupVehicleDistrictResponse;
import com.resustainability.recollect.entity.backend.PickupVehicleDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PickupVehicleDistrictRepository extends JpaRepository<PickupVehicleDistrict, Long> {

    @Query("""
        SELECT 
            pvd.id AS id,
            pvd.isActive AS isActive,
            d.id AS districtId,
            d.districtName AS districtName,
            v.id AS vehicleId,
            v.vehicleName AS vehicleName,
            v.vehicleNumber AS vehicleNumber
        FROM PickupVehicleDistrict pvd
        JOIN pvd.district d
        JOIN pvd.vehicle v
        WHERE (:districtId IS NULL OR pvd.district.id = :districtId)
        AND (:vehicleId IS NULL OR pvd.vehicle.id = :vehicleId)
        AND (:searchTerm IS NULL OR :searchTerm = '' OR 
             d.districtName LIKE CONCAT(:searchTerm, '%'))
    """)
    Page<IPickupVehicleDistrictResponse> findAllPaged(
            @Param("districtId") Long districtId,
            @Param("vehicleId") Long vehicleId,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query("""
        SELECT 
            pvd.id AS id,
            pvd.isActive AS isActive,
            d.id AS districtId,
            d.districtName AS districtName,
            v.id AS vehicleId,
            v.vehicleName AS vehicleName,
            v.vehicleNumber AS vehicleNumber
        FROM PickupVehicleDistrict pvd
        JOIN pvd.district d
        JOIN pvd.vehicle v
        WHERE pvd.id = :id
    """)
    Optional<IPickupVehicleDistrictResponse> findByPickupVehicleDistrictId(@Param("id") Long id);

    @Query("""
        SELECT CASE WHEN COUNT(pvd) > 0 THEN true ELSE false END
        FROM PickupVehicleDistrict pvd
        WHERE pvd.district.id = :districtId AND pvd.vehicle.id = :vehicleId
    """)
    boolean existsByDistrictAndVehicle(@Param("districtId") Long districtId,
                                       @Param("vehicleId") Long vehicleId);
}
