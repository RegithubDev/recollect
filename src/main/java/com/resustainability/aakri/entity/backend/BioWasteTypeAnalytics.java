package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = BioWasteTypeAnalytics.TABLE_NAME)
public class BioWasteTypeAnalytics {
    public static final String TABLE_NAME = "backend_biowastetypeanalytics";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "total_weight", nullable = false)
    private Double totalWeight;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "order_count", nullable = false)
    private Integer orderCount;

    @ManyToOne
    @JoinColumn(name = "biowaste_type_id", nullable = false)
    private BioWasteType biowasteType;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
