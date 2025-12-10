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

	public PickupVehicleDistrict(Long id, Boolean isActive, District district, PickupVehicle vehicle) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.district = district;
		this.vehicle = vehicle;
	}
    
    public PickupVehicleDistrict() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public PickupVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(PickupVehicle vehicle) {
		this.vehicle = vehicle;
	}
    
    
}
