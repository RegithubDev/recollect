package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BioWasteType.TABLE_NAME)
public class BioWasteType {
    public static final String TABLE_NAME = "backend_biowastetype";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biowaste_name", length = 100)
    private String biowasteName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biowaste_category_id", nullable = false)
    private BioWasteCategory biowasteCategory;
}
