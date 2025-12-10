package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = BwgClientMonthlyInvoice.TABLE_NAME)
public class BwgClientMonthlyInvoice {
    public static final String TABLE_NAME = "backend_bwgclientmothlyinvoice";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_month", length = 100)
    private String invoiceMonth;

    @Column(name = "invoice_year")
    private Integer invoiceYear;

    @Column(name = "invoice", length = 100)
    private String invoice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private BwgClient client;

    @Column(name = "bill_amount", nullable = false)
    private Double billAmount;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @Column(name = "settle_date")
    private LocalDate settleDate;
}
