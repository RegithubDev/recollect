package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = ScrapStock.TABLE_NAME)
public class ScrapStock {
    public static final String TABLE_NAME = "backend_scrapstock";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "stock_at", nullable = false)
    private LocalDate stockAt;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "is_manual", nullable = false)
    private Boolean isManual;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "scrap_type_id", nullable = false)
    private ScrapType scrapType;
}
