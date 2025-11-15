package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = OrderUsedBag.TABLE_NAME)
public class OrderUsedBag {
    public static final String TABLE_NAME = "backend_orderusedbag";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_bags", nullable = false)
    private Integer numberOfBags;

    @Column(name = "total_bag_price", nullable = false)
    private Double totalBagPrice;

    @Column(name = "cgst_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal cgstPrice;

    @Column(name = "sgst_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sgstPrice;

    @Column(name = "final_price", nullable = false)
    private Double finalPrice;

    @Column(name = "bag_date")
    private LocalDateTime bagDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private CompleteOrders order;

    @ManyToOne
    @JoinColumn(name = "bag_id", nullable = false)
    private WasteBag bag;
}
