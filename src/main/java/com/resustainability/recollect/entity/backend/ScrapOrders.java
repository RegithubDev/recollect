package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = ScrapOrders.TABLE_NAME)
public class ScrapOrders {
    public static final String TABLE_NAME = "Backend_scraporders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", length = 50)
    private String orderCode;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "alternate_number", length = 20)
    private String alternateNumber;

    @Column(name = "bill_type", length = 20)
    private String billType;

    @Column(name = "prefered_payment_method", length = 20)
    private String preferredPaymentMethod;

    @Column(name = "comment", columnDefinition = "LONGTEXT")
    private String comment;

    @Column(name = "order_rating", nullable = false)
    private Double orderRating;

    @Column(name = "order_status", nullable = false, length = 20)
    private String orderStatus;

    @Column(name = "platform", length = 20)
    private String platform;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private CustomerAddress address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
    private OrderCancelReason reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_region_id")
    private ScrapRegion scrapRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_age", nullable = false)
    private Integer orderAge;

    public ScrapOrders() {
    }

    public ScrapOrders(Long id, String orderCode, LocalDateTime orderDate, LocalDate scheduleDate, String alternateNumber, String billType, String preferredPaymentMethod, String comment, Double orderRating, String orderStatus, String platform, Boolean isDeleted, CustomerAddress address, OrderCancelReason reason, ScrapRegion scrapRegion, State state, Customer customer, Integer orderAge) {
        this.id = id;
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.scheduleDate = scheduleDate;
        this.alternateNumber = alternateNumber;
        this.billType = billType;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.comment = comment;
        this.orderRating = orderRating;
        this.orderStatus = orderStatus;
        this.platform = platform;
        this.isDeleted = isDeleted;
        this.address = address;
        this.reason = reason;
        this.scrapRegion = scrapRegion;
        this.state = state;
        this.customer = customer;
        this.orderAge = orderAge;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ScrapOrders entity = (ScrapOrders) object;
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

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
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

    public Double getOrderRating() {
        return orderRating;
    }

    public void setOrderRating(Double orderRating) {
        this.orderRating = orderRating;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public CustomerAddress getAddress() {
        return address;
    }

    public void setAddress(CustomerAddress address) {
        this.address = address;
    }

    public OrderCancelReason getReason() {
        return reason;
    }

    public void setReason(OrderCancelReason reason) {
        this.reason = reason;
    }

    public ScrapRegion getScrapRegion() {
        return scrapRegion;
    }

    public void setScrapRegion(ScrapRegion scrapRegion) {
        this.scrapRegion = scrapRegion;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getOrderAge() {
        return orderAge;
    }

    public void setOrderAge(Integer orderAge) {
        this.orderAge = orderAge;
    }
}
