package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = Country.TABLE_NAME)
public class Country {
    public static final String TABLE_NAME = "backend_country";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name", length = 100)
    private String countryName;

    @Column(name = "country_code", nullable = false, length = 10)
    private String countryCode;

    @Column(name = "country_image", length = 100)
    private String countryImage;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}
