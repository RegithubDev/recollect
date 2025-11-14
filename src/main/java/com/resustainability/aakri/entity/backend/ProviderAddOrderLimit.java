package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderAddOrderLimit.TABLE_NAME)
public class ProviderAddOrderLimit {
    public static final String TABLE_NAME = "backend_provideraddorderlimit";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "max_limit", nullable = false)
    private Integer maxLimit;

    @Column(name = "current_limit", nullable = false)
    private Integer currentLimit;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
