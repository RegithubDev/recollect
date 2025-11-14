package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = StockSaleItems.TABLE_NAME)
public class StockSaleItems {
    public static final String TABLE_NAME = "backend_stocksaleitems";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "scrap_type_id", nullable = false)
    private ScrapType scrapType;

    @ManyToOne
    @JoinColumn(name = "stock_sale_id", nullable = false)
    private StockSale stockSale;
}
