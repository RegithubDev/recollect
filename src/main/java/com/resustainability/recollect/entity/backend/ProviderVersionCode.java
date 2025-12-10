package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ProviderVersionCode.TABLE_NAME)
public class ProviderVersionCode {
    public static final String TABLE_NAME = "Backend_providerversioncode";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version_code", length = 20)
    private String versionCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
