package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AssignedVehicle.TABLE_NAME)
public class AssignedVehicle {
    public static final String TABLE_NAME = "backend_assignedvehicle";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private PickupVehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
