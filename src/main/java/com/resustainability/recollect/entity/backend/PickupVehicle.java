package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = PickupVehicle.TABLE_NAME)
public class PickupVehicle {
    public static final String TABLE_NAME = "Backend_pickupvehicle";

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


	public PickupVehicle(Long id, String vehicleName, String vehicleNumber, String pickupType, LocalDateTime createdOn,
			String vehicleImage, Boolean isAssigned, Boolean isActive, Boolean isDeleted, State state) {
		super();
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
    
    public PickupVehicle() {
    	
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

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
    
    

}
