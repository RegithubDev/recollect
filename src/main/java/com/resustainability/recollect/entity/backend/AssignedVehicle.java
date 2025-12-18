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
    
    public AssignedVehicle() {
    	
    }

	public AssignedVehicle(Long id, PickupVehicle vehicle, Provider provider) {
		super();
		this.id = id;
		this.vehicle = vehicle;
		this.provider = provider;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PickupVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(PickupVehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
    
    
}
