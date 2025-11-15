package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ScrapType.TABLE_NAME)
public class ScrapType {
    public static final String TABLE_NAME = "backend_scraptype";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_name", length = 100)
    private String scrapName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_payable", nullable = false)
    private Boolean isPayable;

    @Column(name = "is_kg", nullable = false)
    private Boolean isKg;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "scrap_category_id", nullable = false)
    private ScrapCategory scrapCategory;
}
