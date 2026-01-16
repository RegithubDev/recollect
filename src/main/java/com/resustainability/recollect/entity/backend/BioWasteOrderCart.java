package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = BioWasteOrderCart.TABLE_NAME)
public class BioWasteOrderCart {
    public static final String TABLE_NAME = "backend_biowasteordercart";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biowaste_order_id", nullable = false)
    private BioWasteOrders biowasteOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biowaste_type_id", nullable = false)
    private BioWasteType biowasteType;

    @Column(name = "captured_weight", nullable = false)
    private Double capturedWeight;

    @Column(name = "captured_price", nullable = false)
    private Double capturedPrice;

    public BioWasteOrderCart() {
    }

    public BioWasteOrderCart(Long id, Boolean isDeleted, BioWasteOrders biowasteOrder, BioWasteType biowasteType, Double capturedWeight, Double capturedPrice) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.biowasteOrder = biowasteOrder;
        this.biowasteType = biowasteType;
        this.capturedWeight = capturedWeight;
        this.capturedPrice = capturedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BioWasteOrderCart that = (BioWasteOrderCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public BioWasteOrders getBiowasteOrder() {
        return biowasteOrder;
    }

    public void setBiowasteOrder(BioWasteOrders biowasteOrder) {
        this.biowasteOrder = biowasteOrder;
    }

    public BioWasteType getBiowasteType() {
        return biowasteType;
    }

    public void setBiowasteType(BioWasteType biowasteType) {
        this.biowasteType = biowasteType;
    }

    public Double getCapturedWeight() {
        return capturedWeight;
    }

    public void setCapturedWeight(Double capturedWeight) {
        this.capturedWeight = capturedWeight;
    }

    public Double getCapturedPrice() {
        return capturedPrice;
    }

    public void setCapturedPrice(Double capturedPrice) {
        this.capturedPrice = capturedPrice;
    }
}
