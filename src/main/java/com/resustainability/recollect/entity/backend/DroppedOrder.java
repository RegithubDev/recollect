package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = DroppedOrder.TABLE_NAME)
public class DroppedOrder {
    public static final String TABLE_NAME = "backend_droppedorder";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "other_reason", columnDefinition = "LONGTEXT")
    private String otherReason;

    @Column(name = "drop_date", nullable = false)
    private LocalDateTime dropDate;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private CompleteOrders order;

    @ManyToOne
    @JoinColumn(name = "reason_id")
    private OrderDropReason reason;
}
