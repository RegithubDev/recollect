package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = BwgMonthlyContractAmount.TABLE_NAME)
public class BwgMonthlyContractAmount {
    public static final String TABLE_NAME = "Backend_bwgmonthlycontractamount";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_amount")
    private Double contractAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private BwgClient client;

    @Column(name = "contract_cgst")
    private Double contractCgst;

    @Column(name = "contract_sgst")
    private Double contractSgst;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
