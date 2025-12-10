package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderAuthentication.TABLE_NAME)
public class ProviderAuthentication {
    public static final String TABLE_NAME = "Backend_providerauthentication";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp", length = 6)
    private String otp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

	public ProviderAuthentication(Long id, String otp, Provider provider) {
		super();
		this.id = id;
		this.otp = otp;
		this.provider = provider;
	}
    
    public ProviderAuthentication() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
    
    
}
