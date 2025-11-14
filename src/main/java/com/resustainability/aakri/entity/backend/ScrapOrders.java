package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = ScrapOrders.TABLE_NAME)
public class ScrapOrders {
    public static final String TABLE_NAME = "backend_scraporders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", length = 50)
    private String orderCode;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "alternate_number", length = 20)
    private String alternateNumber;

    @Column(name = "bill_type", length = 20)
    private String billType;

    @Column(name = "prefered_payment_method", length = 20)
    private String preferredPaymentMethod;

    @Column(name = "comment", columnDefinition = "LONGTEXT")
    private String comment;

    @Column(name = "order_rating", nullable = false)
    private Double orderRating;

    @Column(name = "order_status", nullable = false, length = 20)
    private String orderStatus;

    @Column(name = "platform", length = 20)
    private String platform;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private CustomerAddress address;

    @ManyToOne
    @JoinColumn(name = "reason_id")
    private OrderCancelReason reason;

    @ManyToOne
    @JoinColumn(name = "scrap_region_id")
    private ScrapRegion scrapRegion;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_age", nullable = false)
    private Integer orderAge;
}
