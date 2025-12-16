package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = ProviderCashLimit.TABLE_NAME)
public class ProviderCashLimit {
    public static final String TABLE_NAME = "backend_providercashlimit";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cash_limit", nullable = false)
    private Double cashLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    public ProviderCashLimit() {
    }

    public ProviderCashLimit(Long id, Double cashLimit, Provider provider) {
        this.id = id;
        this.cashLimit = cashLimit;
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProviderCashLimit that = (ProviderCashLimit) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCashLimit() {
        return cashLimit;
    }

    public void setCashLimit(Double cashLimit) {
        this.cashLimit = cashLimit;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
