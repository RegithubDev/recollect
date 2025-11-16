package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderPickupWeightLimit.TABLE_NAME)
public class ProviderPickupWeightLimit {
    public static final String TABLE_NAME = "backend_providerpickupweightlimit";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_limit", nullable = false)
    private Double weightLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
