package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderDevice.TABLE_NAME)
public class ProviderDevice {
    public static final String TABLE_NAME = "Backend_providerdevice";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", length = 100)
    private String deviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
