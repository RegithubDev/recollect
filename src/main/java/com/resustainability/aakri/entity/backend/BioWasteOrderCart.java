package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BioWasteOrderCart.TABLE_NAME)
public class BioWasteOrderCart {
    public static final String TABLE_NAME = "backend_biowasteordercart";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "biowaste_order_id", nullable = false)
    private BioWasteOrders biowasteOrder;

    @ManyToOne
    @JoinColumn(name = "biowaste_type_id", nullable = false)
    private BioWasteType biowasteType;
}
