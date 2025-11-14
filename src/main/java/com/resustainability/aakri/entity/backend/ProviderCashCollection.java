package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ProviderCashCollection.TABLE_NAME)
public class ProviderCashCollection {
    public static final String TABLE_NAME = "backend_providercashcollection";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type", nullable = false, length = 20)
    private String transactionType;

    @Column(name = "transaction_mode", nullable = false, length = 20)
    private String transactionMode;

    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "reason", nullable = false, length = 20)
    private String reason;

    @Column(name = "other_reason", columnDefinition = "LONGTEXT")
    private String otherReason;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "done_by", length = 100)
    private String doneBy;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private BwgClient client;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CompleteOrders order;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private PickupVehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
