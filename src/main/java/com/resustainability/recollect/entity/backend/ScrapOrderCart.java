package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_order_id", nullable = false)
    private ScrapOrders scrapOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_type_id", nullable = false)
    private ScrapType scrapType;

    @Column(name = "captured_weight", nullable = false)
    private Double capturedWeight;

    @Column(name = "captured_price", nullable = false)
    private Double capturedPrice;

    public ScrapOrderCart() {
    }

    public ScrapOrderCart(Long id, Double scrapWeight, Double scrapPrice, Double totalPrice, Boolean isDeleted, ScrapOrders scrapOrder, ScrapType scrapType, Double capturedWeight, Double capturedPrice) {
        this.id = id;
        this.scrapWeight = scrapWeight;
        this.scrapPrice = scrapPrice;
        this.totalPrice = totalPrice;
        this.isDeleted = isDeleted;
        this.scrapOrder = scrapOrder;
        this.scrapType = scrapType;
        this.capturedWeight = capturedWeight;
        this.capturedPrice = capturedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ScrapOrderCart that = (ScrapOrderCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScrapWeight() {
        return scrapWeight;
    }

    public void setScrapWeight(Double scrapWeight) {
        this.scrapWeight = scrapWeight;
    }

    public Double getScrapPrice() {
        return scrapPrice;
    }

    public void setScrapPrice(Double scrapPrice) {
        this.scrapPrice = scrapPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public ScrapOrders getScrapOrder() {
        return scrapOrder;
    }

    public void setScrapOrder(ScrapOrders scrapOrder) {
        this.scrapOrder = scrapOrder;
    }

    public ScrapType getScrapType() {
        return scrapType;
    }

    public void setScrapType(ScrapType scrapType) {
        this.scrapType = scrapType;
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
