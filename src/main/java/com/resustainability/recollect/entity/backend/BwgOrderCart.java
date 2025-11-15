package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BwgOrderCart.TABLE_NAME)
public class BwgOrderCart {
    public static final String TABLE_NAME = "backend_bwgordercart";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_weight", nullable = false)
    private Double scrapWeight;

    @Column(name = "scrap_price", nullable = false)
    private Double scrapPrice;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "biowaste_type_id")
    private BioWasteType bioWasteType;

    @ManyToOne
    @JoinColumn(name = "bwg_order_id", nullable = false)
    private BwgOrders bwgOrder;

    @ManyToOne
    @JoinColumn(name = "scrap_type_id")
    private ScrapType scrapType;

    @Column(name = "scrap_gst", nullable = false)
    private Double scrapGst;

    @Column(name = "scrap_hsn", length = 10)
    private String scrapHsn;
}
