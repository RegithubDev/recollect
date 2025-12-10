package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderAddOrderLimit.TABLE_NAME)
public class ProviderAddOrderLimit {
    public static final String TABLE_NAME = "Backend_provideraddorderlimit";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_limit", nullable = false)
    private Integer maxLimit;

    @Column(name = "current_limit", nullable = false)
    private Integer currentLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
    
    public ProviderAddOrderLimit() {
    	
    }
    

	public ProviderAddOrderLimit(Long id, Integer maxLimit, Integer currentLimit, Provider provider) {
		super();
		this.id = id;
		this.maxLimit = maxLimit;
		this.currentLimit = currentLimit;
		this.provider = provider;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
	}

	public Integer getCurrentLimit() {
		return currentLimit;
	}

	public void setCurrentLimit(Integer currentLimit) {
		this.currentLimit = currentLimit;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}
    
    
}
