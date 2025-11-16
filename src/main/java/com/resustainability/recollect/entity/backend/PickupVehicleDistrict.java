package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = PickupVehicleDistrict.TABLE_NAME)
public class PickupVehicleDistrict {
    public static final String TABLE_NAME = "backend_pickupvehicledistrict";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private PickupVehicle vehicle;
}
