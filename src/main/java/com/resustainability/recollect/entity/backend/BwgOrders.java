package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = BwgOrders.TABLE_NAME)
public class BwgOrders {
    public static final String TABLE_NAME = "backend_bwgorders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", length = 50)
    private String orderCode;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "order_rating", nullable = false)
    private Double orderRating;

    @Column(name = "order_type", length = 20)
    private String orderType;

    @Column(name = "bill_type", length = 20)
    private String billType;

    @Column(name = "prefered_payment_method", length = 20)
    private String preferredPaymentMethod;

    @Column(name = "comment", columnDefinition = "LONGTEXT")
    private String comment;

    @Column(name = "order_status", nullable = false, length = 20)
    private String orderStatus;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private BwgClient client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "due_settled", nullable = false)
    private Boolean dueSettled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
    private OrderCancelReason reason;
}
