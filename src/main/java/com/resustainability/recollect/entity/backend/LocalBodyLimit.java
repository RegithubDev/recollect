package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
    name = LocalBodyLimit.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"available_date","localbody_id"})
)
public class LocalBodyLimit {
    public static final String TABLE_NAME = "Backend_localbodylimit";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Column(name = "limit", nullable = false)
    private Integer limit;

    @Column(name = "remaining_slots", nullable = false)
    private Integer remainingSlots;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localbody_id", nullable = false)
    private LocalBody localbody;
}
