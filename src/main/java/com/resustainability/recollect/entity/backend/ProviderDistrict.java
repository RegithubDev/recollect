package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderDistrict.TABLE_NAME)
public class ProviderDistrict {
    public static final String TABLE_NAME = "backend_providerdistrict";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_allowed", nullable = false)
    private Boolean scrapAllowed;

    @Column(name = "bio_allowed", nullable = false)
    private Boolean bioAllowed;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
