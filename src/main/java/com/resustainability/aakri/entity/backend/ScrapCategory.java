package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ScrapCategory.TABLE_NAME)
public class ScrapCategory {
    public static final String TABLE_NAME = "backend_scrapcategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "subcategory_name", nullable = false, length = 10)
    private String subcategoryName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "hsn_code", length = 10)
    private String hsnCode;
}
