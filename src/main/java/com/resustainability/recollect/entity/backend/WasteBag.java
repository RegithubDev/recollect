package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = WasteBag.TABLE_NAME)
public class WasteBag {
    public static final String TABLE_NAME = "backend_wastebag";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bag_size", nullable = false, length = 20)
    private String bagSize;

    @Column(name = "bag_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal bagPrice;

    @Column(name = "bag_cgst", nullable = false)
    private Double bagCgst;

    @Column(name = "bag_sgst", nullable = false)
    private Double bagSgst;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "is_bwg", nullable = false)
    private Boolean isBwg;
}
