package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = OrderVisibility.TABLE_NAME)
public class OrderVisibility {
    public static final String TABLE_NAME = "Backend_ordervisibility";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
}
