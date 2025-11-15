package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = CustomerScrapMoney.TABLE_NAME)
public class CustomerScrapMoney {
    public static final String TABLE_NAME = "backend_customerscrapmoney";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_money", nullable = false)
    private Double scrapMoney;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private BwgClient client;
}
