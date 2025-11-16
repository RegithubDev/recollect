package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = PickupVehicle.TABLE_NAME)
public class PickupVehicle {
    public static final String TABLE_NAME = "backend_pickupvehicle";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_name", length = 100)
    private String vehicleName;

    @Column(name = "vehicle_number", length = 20)
    private String vehicleNumber;

    @Column(name = "pickup_type", length = 20)
    private String pickupType;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "vehicle_image", length = 100)
    private String vehicleImage;

    @Column(name = "is_assigned", nullable = false)
    private Boolean isAssigned;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;
}
