package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ServiceCategory.TABLE_NAME)
public class ServiceCategory {
    public static final String TABLE_NAME = "backend_servicecategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "service_title", nullable = false, columnDefinition = "LONGTEXT")
    private String serviceTitle;

    @Column(name = "service_subtitle", columnDefinition = "LONGTEXT")
    private String serviceSubtitle;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled;
}
