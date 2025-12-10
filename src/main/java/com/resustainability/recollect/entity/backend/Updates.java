package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = Updates.TABLE_NAME)
public class Updates {
    public static final String TABLE_NAME = "Backend_updates";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image", nullable = false, length = 100)
    private String image;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "repeatation", nullable = false, length = 50)
    private String repeatation;
}
