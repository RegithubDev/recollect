package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ScrapTypeLocationAndPrice.TABLE_NAME)
public class ScrapTypeLocationAndPrice {
    public static final String TABLE_NAME = "backend_scraptypelocationandprice";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_price")
    private Double scrapPrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "scrap_type_id", nullable = false)
    private ScrapType scrapType;

    @Column(name = "scrap_cgst")
    private Double scrapCgst;

    @Column(name = "scrap_sgst")
    private Double scrapSgst;
}
