package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = OrderCancelReason.TABLE_NAME)
public class OrderCancelReason {
    public static final String TABLE_NAME = "backend_ordercancelreason";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason", nullable = false, columnDefinition = "LONGTEXT")
    private String reason;

    @Column(name = "order_type", nullable = false, length = 10)
    private String orderType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public OrderCancelReason() {
    }

    public OrderCancelReason(Long id, String reason, String orderType, Boolean isActive) {
        this.id = id;
        this.reason = reason;
        this.orderType = orderType;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderCancelReason that = (OrderCancelReason) object;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
