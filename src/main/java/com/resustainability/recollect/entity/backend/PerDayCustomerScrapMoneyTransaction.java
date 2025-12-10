package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
    name = PerDayCustomerScrapMoneyTransaction.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id","transaction_date"})
)
public class PerDayCustomerScrapMoneyTransaction {
    public static final String TABLE_NAME = "Backend_perdaycustomerscrapmoneytransaction";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
