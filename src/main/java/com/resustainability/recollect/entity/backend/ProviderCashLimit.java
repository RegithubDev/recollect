package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderCashLimit.TABLE_NAME)
public class ProviderCashLimit {
    public static final String TABLE_NAME = "Backend_providercashlimit";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cash_limit", nullable = false)
    private Double cashLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
