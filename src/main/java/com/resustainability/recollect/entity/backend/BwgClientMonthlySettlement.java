package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = BwgClientMonthlySettlement.TABLE_NAME)
public class BwgClientMonthlySettlement {
    public static final String TABLE_NAME = "Backend_bwgclientmonthlysettlement";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_partial", nullable = false)
    private Boolean isPartial;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "partial_balance", nullable = false)
    private Double partialBalance;

    @Column(name = "done_by", columnDefinition = "LONGTEXT")
    private String doneBy;

    @Column(name = "remark", columnDefinition = "LONGTEXT")
    private String remark;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminUser admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwg_month_invoice_id", nullable = false)
    private BwgClientMonthlyInvoice monthlyInvoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private BwgClient client;

    @Column(name = "settle_mode", length = 100)
    private String settleMode;
}
