package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = WalletTransactions.TABLE_NAME)
public class WalletTransactions {
    public static final String TABLE_NAME = "backend_wallettransactions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type", nullable = false, length = 10)
    private String transactionType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @Column(name = "transaction_mode", length = 20)
    private String transactionMode;

    @Column(name = "wallet_balance", nullable = false)
    private Double walletBalance;

    @Column(name = "done_by", length = 100)
    private String doneBy;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
