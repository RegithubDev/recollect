package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = WardAvailability.TABLE_NAME)
public class WardAvailability {
    public static final String TABLE_NAME = "backend_wardavailability";

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
    @JoinColumn(name = "localbody_limit_id", nullable = false)
    private LocalBodyLimit localbodyLimit;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;
}
