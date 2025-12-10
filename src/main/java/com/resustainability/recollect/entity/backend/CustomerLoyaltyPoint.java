package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = CustomerLoyaltyPoint.TABLE_NAME)
public class CustomerLoyaltyPoint {
    public static final String TABLE_NAME = "backend_customerloyaltypoint";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "points", nullable = false)
    private Double points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
