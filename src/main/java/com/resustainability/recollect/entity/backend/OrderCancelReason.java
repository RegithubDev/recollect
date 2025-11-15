package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = OrderCancelReason.TABLE_NAME)
public class OrderCancelReason {
    public static final String TABLE_NAME = "backend_ordercancelreason";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason", nullable = false, columnDefinition = "LONGTEXT")
    private String reason;

    @Column(name = "order_type", nullable = false, length = 10)
    private String orderType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
