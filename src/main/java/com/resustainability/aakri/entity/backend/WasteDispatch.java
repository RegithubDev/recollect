package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = WasteDispatch.TABLE_NAME)
public class WasteDispatch {
    public static final String TABLE_NAME = "backend_wastedispatch";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "waste_weight", nullable = false)
    private Double wasteWeight;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private WasteDispatchCompany company;

    @Column(name = "dispatch_date")
    private LocalDate dispatchDate;
}
