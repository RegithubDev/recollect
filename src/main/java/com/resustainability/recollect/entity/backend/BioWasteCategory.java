package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BioWasteCategory.TABLE_NAME)
public class BioWasteCategory {
    public static final String TABLE_NAME = "backend_biowastecategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
