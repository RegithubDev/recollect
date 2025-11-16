package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = CompleteOrders.TABLE_NAME)
public class CompleteOrders {
    public static final String TABLE_NAME = "backend_completeorders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    @Column(name = "order_type", length = 20)
    private String orderType;

    @Column(name = "order_status", nullable = false, length = 20)
    private String orderStatus;

    @Column(name = "bio_total_bill_amount")
    private Double bioTotalBillAmount;

    @Column(name = "bio_subsidy_amount")
    private Double bioSubsidyAmount;

    @Column(name = "bio_bill_amount")
    private Double bioBillAmount;

    @Column(name = "bio_cgst_amount")
    private Double bioCgstAmount;

    @Column(name = "bio_sgst_amount")
    private Double bioSgstAmount;

    @Column(name = "bio_bag_amount")
    private Double bioBagAmount;

    @Column(name = "bio_total_bill")
    private Double bioTotalBill;

    @Column(name = "bio_wallet_deduct")
    private Double bioWalletDeduct;

    @Column(name = "bio_weight", nullable = false)
    private Double bioWeight;

    @Column(name = "scrap_total_bill")
    private Double scrapTotalBill;

    @Column(name = "scrap_wallet_deduct")
    private Double scrapWalletDeduct;

    @Column(name = "scrap_service_charge")
    private Double scrapServiceCharge;

    @Column(name = "scrap_roundoff")
    private Double scrapRoundoff;

    @Column(name = "final_bill")
    private Double finalBill;

    @Column(name = "payment_method", length = 20)
    private String paymentMethod;

    @Column(name = "is_selected", nullable = false)
    private Boolean isSelected;

    @Column(name = "billed_at")
    private LocalDateTime billedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "cancel_request", nullable = false)
    private Boolean cancelRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biowaste_order_id")
    private BioWasteOrders bioWasteOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwg_order_id")
    private BwgOrders bwgOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private BwgClient client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private PickupVehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_order_id")
    private ScrapOrders scrapOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
