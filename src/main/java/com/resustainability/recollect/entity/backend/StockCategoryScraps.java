package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = StockCategoryScraps.TABLE_NAME)
public class StockCategoryScraps {
    public static final String TABLE_NAME = "Backend_stockcategory_scraps";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockcategory_id", nullable = false)
    private StockCategory stockCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scraptype_id", nullable = false)
    private ScrapType scrapType;
}
