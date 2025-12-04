package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public PickupVehicle() {
    }

    public PickupVehicle(Long id, String vehicleName, String vehicleNumber, String pickupType, LocalDateTime createdOn, String vehicleImage, Boolean isAssigned, Boolean isActive, Boolean isDeleted, State state) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.pickupType = pickupType;
        this.createdOn = createdOn;
        this.vehicleImage = vehicleImage;
        this.isAssigned = isAssigned;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.state = state;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PickupVehicle entity = (PickupVehicle) object;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public Boolean getAssigned() {
        return isAssigned;
    }

    public void setAssigned(Boolean assigned) {
        isAssigned = assigned;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
