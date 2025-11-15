package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = StockSale.TABLE_NAME)
public class StockSale {
    public static final String TABLE_NAME = "backend_stocksale";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type", nullable = false, length = 20)
    private String transactionType;

    @Column(name = "is_sale", nullable = false)
    private Boolean isSale;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "sale_on", nullable = false)
    private LocalDate saleOn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private StockCategory category;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendors vendor;
}
