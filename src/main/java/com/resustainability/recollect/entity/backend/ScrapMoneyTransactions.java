package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ScrapMoneyTransactions.TABLE_NAME)
public class ScrapMoneyTransactions {
    public static final String TABLE_NAME = "backend_scrapmoneytransactions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @Column(name = "verification_fee", nullable = false)
    private Double verificationFee;

    @Column(name = "verification_fee_tax", nullable = false)
    private Double verificationFeeTax;

    @Column(name = "transaction_fee", nullable = false)
    private Double transactionFee;

    @Column(name = "transaction_fee_tax", nullable = false)
    private Double transactionFeeTax;

    @Column(name = "final_amount", nullable = false)
    private Double finalAmount;

    @Column(name = "scrap_money_balance", nullable = false)
    private Double scrapMoneyBalance;

    @Column(name = "transaction_type", length = 100)
    private String transactionType;

    @Column(name = "transaction_reason", length = 100)
    private String transactionReason;

    @Column(name = "done_by", length = 250)
    private String doneBy;

    @Column(name = "request_status", length = 100)
    private String requestStatus;

    @Column(name = "transaction_utr", length = 250)
    private String transactionUtr;

    @Column(name = "fail_reason", columnDefinition = "LONGTEXT")
    private String failReason;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private CustomerPaymentMethods paymentMethod;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "payout_type", nullable = false, length = 100)
    private String payoutType;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private BwgClient client;
}
