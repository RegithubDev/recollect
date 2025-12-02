package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderServiceLocationFlag.TABLE_NAME)
public class ProviderServiceLocationFlag {
    public static final String TABLE_NAME = "backend_providerservicelocationflag";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "all_scrap_district", nullable = false)
    private Boolean allScrapDistrict;

    @Column(name = "all_bio_district", nullable = false)
    private Boolean allBioDistrict;

    @Column(name = "all_scrap_regions", nullable = false)
    private Boolean allScrapRegions;

    @Column(name = "all_localbodies", nullable = false)
    private Boolean allLocalBodies;

    @Column(name = "all_wards", nullable = false)
    private Boolean allWards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

	public ProviderServiceLocationFlag(Long id, Boolean allScrapDistrict, Boolean allBioDistrict,
			Boolean allScrapRegions, Boolean allLocalBodies, Boolean allWards, Provider provider) {
		super();
		this.id = id;
		this.allScrapDistrict = allScrapDistrict;
		this.allBioDistrict = allBioDistrict;
		this.allScrapRegions = allScrapRegions;
		this.allLocalBodies = allLocalBodies;
		this.allWards = allWards;
		this.provider = provider;
	}
    
    public ProviderServiceLocationFlag() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAllScrapDistrict() {
		return allScrapDistrict;
	}

	public void setAllScrapDistrict(Boolean allScrapDistrict) {
		this.allScrapDistrict = allScrapDistrict;
	}

	public Boolean getAllBioDistrict() {
		return allBioDistrict;
	}

	public void setAllBioDistrict(Boolean allBioDistrict) {
		this.allBioDistrict = allBioDistrict;
	}

	public Boolean getAllScrapRegions() {
		return allScrapRegions;
	}

	public void setAllScrapRegions(Boolean allScrapRegions) {
		this.allScrapRegions = allScrapRegions;
	}

	public Boolean getAllLocalBodies() {
		return allLocalBodies;
	}

	public void setAllLocalBodies(Boolean allLocalBodies) {
		this.allLocalBodies = allLocalBodies;
	}

	public Boolean getAllWards() {
		return allWards;
	}

	public void setAllWards(Boolean allWards) {
		this.allWards = allWards;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
    
    
}
