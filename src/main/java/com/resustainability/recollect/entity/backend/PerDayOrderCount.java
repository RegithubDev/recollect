package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = PerDayOrderCount.TABLE_NAME)
public class PerDayOrderCount {
    public static final String TABLE_NAME = "backend_perdayordercount";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "bio_count_open", nullable = false)
    private Integer bioCountOpen;

    @Column(name = "scrap_count_open", nullable = false)
    private Integer scrapCountOpen;

    @Column(name = "bio_count_pending", nullable = false)
    private Integer bioCountPending;

    @Column(name = "scrap_count_pending", nullable = false)
    private Integer scrapCountPending;
}
