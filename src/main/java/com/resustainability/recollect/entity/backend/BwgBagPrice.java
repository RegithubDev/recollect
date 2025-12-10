package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BwgBagPrice.TABLE_NAME)
public class BwgBagPrice {
    public static final String TABLE_NAME = "backend_bwgbagprice";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bag_size", nullable = false, length = 20)
    private String bagSize;

    @Column(name = "bag_price", nullable = false)
    private Double bagPrice;

    @Column(name = "bag_cgst", nullable = false)
    private Double bagCgst;

    @Column(name = "bag_sgst", nullable = false)
    private Double bagSgst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private BwgClient client;
}
