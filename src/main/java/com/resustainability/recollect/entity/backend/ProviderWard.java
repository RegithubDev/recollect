package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderWard.TABLE_NAME)
public class ProviderWard {
    public static final String TABLE_NAME = "Backend_providerward";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

	public ProviderWard(Long id, Boolean isActive, Provider provider, Ward ward) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.provider = provider;
		this.ward = ward;
	}
    
    public ProviderWard() {
    	
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

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}
    
    
}
