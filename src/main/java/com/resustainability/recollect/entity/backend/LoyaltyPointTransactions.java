package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = LoyaltyPointTransactions.TABLE_NAME)
public class LoyaltyPointTransactions {
    public static final String TABLE_NAME = "backend_loyaltypointtransactions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type", nullable = false, length = 10)
    private String transactionType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "transaction_point", nullable = false)
    private Double transactionPoint;

    @Column(name = "transaction_mode", length = 20)
    private String transactionMode;

    @Column(name = "point_balance", nullable = false)
    private Double pointBalance;

    @Column(name = "done_by", length = 100)
    private String doneBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
