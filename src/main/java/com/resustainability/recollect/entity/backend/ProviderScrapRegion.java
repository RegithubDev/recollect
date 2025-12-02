package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderScrapRegion.TABLE_NAME)
public class ProviderScrapRegion {
    public static final String TABLE_NAME = "backend_providerscrapregion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_region_id", nullable = false)
    private ScrapRegion scrapRegion;

	public ProviderScrapRegion(Long id, Boolean isActive, Provider provider, ScrapRegion scrapRegion) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.provider = provider;
		this.scrapRegion = scrapRegion;
	}
    
    
    public ProviderScrapRegion() {
    	
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


	public ScrapRegion getScrapRegion() {
		return scrapRegion;
	}


	public void setScrapRegion(ScrapRegion scrapRegion) {
		this.scrapRegion = scrapRegion;
	}
    
    
}
