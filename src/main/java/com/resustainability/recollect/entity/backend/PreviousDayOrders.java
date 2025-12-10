package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = PreviousDayOrders.TABLE_NAME)
public class PreviousDayOrders {
    public static final String TABLE_NAME = "backend_previousdayorders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "scrap_pending_order_count", nullable = false)
    private Integer scrapPendingOrderCount;

    @Column(name = "bio_pending_order_count", nullable = false)
    private Integer bioPendingOrderCount;

    @Column(name = "scrap_scheduled_order_count", nullable = false)
    private Integer scrapScheduledOrderCount;

    @Column(name = "bio_scheduled_order_count", nullable = false)
    private Integer bioScheduledOrderCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;
}
