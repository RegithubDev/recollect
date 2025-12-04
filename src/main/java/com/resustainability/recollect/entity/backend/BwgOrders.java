package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = BwgOrders.TABLE_NAME)
public class BwgOrders {
    public static final String TABLE_NAME = "backend_bwgorders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", length = 50)
    private String orderCode;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "order_rating", nullable = false)
    private Double orderRating;

    @Column(name = "order_type", length = 20)
    private String orderType;

    @Column(name = "bill_type", length = 20)
    private String billType;

    @Column(name = "prefered_payment_method", length = 20)
    private String preferredPaymentMethod;

    @Column(name = "comment", columnDefinition = "LONGTEXT")
    private String comment;

    @Column(name = "order_status", nullable = false, length = 20)
    private String orderStatus;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private BwgClient client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "due_settled", nullable = false)
    private Boolean dueSettled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
    private OrderCancelReason reason;

    public BwgOrders() {
    }

    public BwgOrders(Long id, String orderCode, LocalDateTime orderDate, LocalDate scheduleDate, Double orderRating, String orderType, String billType, String preferredPaymentMethod, String comment, String orderStatus, Boolean isDeleted, BwgClient client, State state, Boolean dueSettled, OrderCancelReason reason) {
        this.id = id;
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.scheduleDate = scheduleDate;
        this.orderRating = orderRating;
        this.orderType = orderType;
        this.billType = billType;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.comment = comment;
        this.orderStatus = orderStatus;
        this.isDeleted = isDeleted;
        this.client = client;
        this.state = state;
        this.dueSettled = dueSettled;
        this.reason = reason;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BwgOrders entity = (BwgOrders) object;
        return Objects.equals(id, entity.id);
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Double getOrderRating() {
        return orderRating;
    }

    public void setOrderRating(Double orderRating) {
        this.orderRating = orderRating;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public BwgClient getClient() {
        return client;
    }

    public void setClient(BwgClient client) {
        this.client = client;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getDueSettled() {
        return dueSettled;
    }

    public void setDueSettled(Boolean dueSettled) {
        this.dueSettled = dueSettled;
    }

    public OrderCancelReason getReason() {
        return reason;
    }

    public void setReason(OrderCancelReason reason) {
        this.reason = reason;
    }
}
