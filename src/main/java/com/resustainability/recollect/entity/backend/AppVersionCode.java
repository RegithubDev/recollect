package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = AppVersionCode.TABLE_NAME)
public class AppVersionCode {
    public static final String TABLE_NAME = "backend_appversioncode";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform", nullable = false, length = 50)
    private String platform;

    @Column(name = "version_code", nullable = false, length = 50)
    private String versionCode;

    @Column(name = "force_update", nullable = false)
    private Boolean forceUpdate;

    @Column(name = "message", columnDefinition = "LONGTEXT")
    private String message;

    @Column(name = "stable_version", columnDefinition = "LONGTEXT")
    private String stableVersion;

    @Column(name = "link", columnDefinition = "LONGTEXT")
    private String link;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "application", nullable = false, length = 50)
    private String application;
}
