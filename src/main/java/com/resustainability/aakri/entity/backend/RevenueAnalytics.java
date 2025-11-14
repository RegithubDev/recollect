package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = RevenueAnalytics.TABLE_NAME)
public class RevenueAnalytics {
    public static final String TABLE_NAME = "backend_revenueanalytics";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "scrap_revenue_positive", nullable = false)
    private Double scrapRevenuePositive;

    @Column(name = "scrap_revenue_negative", nullable = false)
    private Double scrapRevenueNegative;

    @Column(name = "scrap_order_count", nullable = false)
    private Integer scrapOrderCount;

    @Column(name = "bio_revenue", nullable = false)
    private Double bioRevenue;

    @Column(name = "bio_order_count", nullable = false)
    private Integer bioOrderCount;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "localbody_id")
    private LocalBody localBody;

    @ManyToOne
    @JoinColumn(name = "scrap_region_id")
    private ScrapRegion scrapRegion;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;
}
