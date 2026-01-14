package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = CustomerTermsAndConditions.TABLE_NAME)
public class CustomerTermsAndConditions {
    public static final String TABLE_NAME = "backend_customertermsandconditions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "optional", nullable = false)
    private Boolean optional;

    @Column(name = "signed_date", nullable = false)
    private LocalDateTime signedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public CustomerTermsAndConditions() {
    }

    public CustomerTermsAndConditions(Long id, Boolean optional, LocalDateTime signedDate, Customer customer) {
        this.id = id;
        this.optional = optional;
        this.signedDate = signedDate;
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CustomerTermsAndConditions that = (CustomerTermsAndConditions) o;
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

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public LocalDateTime getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(LocalDateTime signedDate) {
        this.signedDate = signedDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
