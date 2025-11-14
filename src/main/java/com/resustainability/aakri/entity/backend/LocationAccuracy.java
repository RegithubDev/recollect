package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = LocationAccuracy.TABLE_NAME)
public class LocationAccuracy {
    public static final String TABLE_NAME = "backend_locationaccuracy";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accuracy", nullable = false)
    private Boolean accuracy;
}
