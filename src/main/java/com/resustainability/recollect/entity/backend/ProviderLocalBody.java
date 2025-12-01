package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderLocalBody.TABLE_NAME)
public class ProviderLocalBody {
    public static final String TABLE_NAME = "backend_providerlocalbody";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localbody_id", nullable = false)
    private LocalBody localBody;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

	public ProviderLocalBody(Long id, Boolean isActive, LocalBody localBody, Provider provider) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.localBody = localBody;
		this.provider = provider;
	}
    
    public ProviderLocalBody() {
    	
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

	public LocalBody getLocalBody() {
		return localBody;
	}

	public void setLocalBody(LocalBody localBody) {
		this.localBody = localBody;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
    
    
}
