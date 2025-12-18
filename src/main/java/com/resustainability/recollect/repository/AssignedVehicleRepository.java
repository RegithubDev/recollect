package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IAssignedVehicleResponse;
import com.resustainability.recollect.entity.backend.AssignedVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AssignedVehicleRepository extends JpaRepository<AssignedVehicle, Long> {

    @Query("""
        SELECT
            av.id AS id,
            v.id AS vehicleId,
            v.vehicleNumber AS vehicleNumber,
            v.vehicleName AS vehicleName,
            p.id AS providerId,
            p.fullName AS providerName
        FROM AssignedVehicle av
        JOIN av.vehicle v
        JOIN av.provider p
    """)
    Page<IAssignedVehicleResponse> findAllPaged(Pageable pageable);

    @Query("""
        SELECT
            av.id AS id,
            v.id AS vehicleId,
            v.vehicleNumber AS vehicleNumber,
            v.vehicleName AS vehicleName,
            p.id AS providerId,
            p.fullName AS providerName
        FROM AssignedVehicle av
        JOIN av.vehicle v
        JOIN av.provider p
        WHERE av.id = :id
    """)
    Optional<IAssignedVehicleResponse> findByAssignedVehicleId(
            @Param("id") Long id
    );

    boolean existsByVehicleIdAndProviderId(Long vehicleId, Long providerId);
}
