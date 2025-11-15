package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ScrapOrderCart.TABLE_NAME)
public class ScrapOrderCart {
    public static final String TABLE_NAME = "backend_scrapordercart";

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
    @JoinColumn(name = "scrap_order_id", nullable = false)
    private ScrapOrders scrapOrder;

    @ManyToOne
    @JoinColumn(name = "scrap_type_id", nullable = false)
    private ScrapType scrapType;
}
