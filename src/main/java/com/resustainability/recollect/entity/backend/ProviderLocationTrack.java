package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ProviderLocationTrack.TABLE_NAME)
public class ProviderLocationTrack {
    public static final String TABLE_NAME = "backend_providerlocationtrack";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "loc_time", nullable = false)
    private LocalDateTime locTime;

    @Column(name = "unique_id", length = 100)
    private String uniqueId;

    @Column(name = "device_model", length = 100)
    private String deviceModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "battery_level", length = 100)
    private String batteryLevel;

    @Column(name = "carrier", length = 100)
    private String carrier;

    @Column(name = "device_brand", length = 100)
    private String deviceBrand;

    @Column(name = "device_manufacturer", length = 100)
    private String deviceManufacturer;

    @Column(name = "device_type", length = 100)
    private String deviceType;

    @Column(name = "ip_address", length = 100)
    private String ipAddress;

    @Column(name = "device_id", length = 100)
    private String deviceId;
}
