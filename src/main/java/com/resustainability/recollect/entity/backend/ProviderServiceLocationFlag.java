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

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
