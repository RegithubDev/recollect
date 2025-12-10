package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = CustomerOrderAnalytics.TABLE_NAME)
public class CustomerOrderAnalytics {
    public static final String TABLE_NAME = "backend_customerorderanalytics";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "scrap_revenue", nullable = false)
    private Double scrapRevenue;

    @Column(name = "bio_revenue", nullable = false)
    private Double bioRevenue;

    @Column(name = "scrap_weight", nullable = false)
    private Double scrapWeight;

    @Column(name = "bio_weight", nullable = false)
    private Double bioWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localbody_id")
    private LocalBody localbody;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_region_id")
    private ScrapRegion scrapRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private BwgClient client;
}
