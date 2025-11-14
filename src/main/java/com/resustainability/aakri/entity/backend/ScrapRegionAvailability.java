package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = ScrapRegionAvailability.TABLE_NAME)
public class ScrapRegionAvailability {
    public static final String TABLE_NAME = "backend_scrapregionavailability";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Column(name = "limit", nullable = false)
    private Integer limit;

    @Column(name = "remaining_slots", nullable = false)
    private Integer remainingSlots;

    @ManyToOne
    @JoinColumn(name = "scrap_region_id", nullable = false)
    private ScrapRegion scrapRegion;
}
