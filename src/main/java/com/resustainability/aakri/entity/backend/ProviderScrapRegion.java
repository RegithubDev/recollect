package com.resustainability.aakri.entity.backend;

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

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "scrap_region_id", nullable = false)
    private ScrapRegion scrapRegion;
}
