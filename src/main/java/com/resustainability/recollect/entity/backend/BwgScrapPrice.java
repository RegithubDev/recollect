package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BwgScrapPrice.TABLE_NAME)
public class BwgScrapPrice {
    public static final String TABLE_NAME = "backend_bwgscrapprice";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_price", nullable = false)
    private Double scrapPrice;

    @ManyToOne
    @JoinColumn(name = "scrap_type_id", nullable = false)
    private ScrapType scrapType;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private BwgClient client;
}
