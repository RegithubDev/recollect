package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderDistrict.TABLE_NAME)
public class ProviderDistrict {
    public static final String TABLE_NAME = "Backend_providerdistrict";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_allowed", nullable = false)
    private Boolean scrapAllowed;

    @Column(name = "bio_allowed", nullable = false)
    private Boolean bioAllowed;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
    
    
    public ProviderDistrict() {
    	
    }

	public ProviderDistrict(Long id, Boolean scrapAllowed, Boolean bioAllowed, Boolean isActive, District district,
			Provider provider) {
		super();
		this.id = id;
		this.scrapAllowed = scrapAllowed;
		this.bioAllowed = bioAllowed;
		this.isActive = isActive;
		this.district = district;
		this.provider = provider;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getScrapAllowed() {
		return scrapAllowed;
	}

	public void setScrapAllowed(Boolean scrapAllowed) {
		this.scrapAllowed = scrapAllowed;
	}

	public Boolean getBioAllowed() {
		return bioAllowed;
	}

	public void setBioAllowed(Boolean bioAllowed) {
		this.bioAllowed = bioAllowed;
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

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	
    
}
