package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = StockCategory.TABLE_NAME)
public class StockCategory {
    public static final String TABLE_NAME = "backend_stockcategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;
}
