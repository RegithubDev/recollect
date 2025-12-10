package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderPickupWeightLimit.TABLE_NAME)
public class ProviderPickupWeightLimit {
    public static final String TABLE_NAME = "backend_providerpickupweightlimit";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_limit", nullable = false)
    private Double weightLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

	public ProviderPickupWeightLimit(Long id, Double weightLimit, Provider provider) {
		super();
		this.id = id;
		this.weightLimit = weightLimit;
		this.provider = provider;
	}
    
    public ProviderPickupWeightLimit() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(Double weightLimit) {
		this.weightLimit = weightLimit;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
    
    
}
