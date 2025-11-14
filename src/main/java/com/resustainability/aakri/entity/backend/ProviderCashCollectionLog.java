package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ProviderCashCollectionLog.TABLE_NAME)
public class ProviderCashCollectionLog {
    public static final String TABLE_NAME = "backend_providercashcollectionlog";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation", nullable = false, length = 10)
    private String operation;

    @Column(name = "old_transaction_mode", nullable = false, length = 10)
    private String oldTransactionMode;

    @Column(name = "new_transaction_mode", nullable = false, length = 10)
    private String newTransactionMode;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "old_balance")
    private Double oldBalance;

    @Column(name = "new_balance")
    private Double newBalance;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @ManyToOne
    @JoinColumn(name = "cash_collection_id")
    private ProviderCashCollection cashCollection;

    @ManyToOne
    @JoinColumn(name = "done_by_id")
    private Provider doneBy;
}
