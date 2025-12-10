package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = CustomerDonation.TABLE_NAME)
public class CustomerDonation {
    public static final String TABLE_NAME = "Backend_customerdonation";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "donation_date", nullable = false)
    private LocalDateTime donationDate;

    @Column(name = "donation_amount", nullable = false)
    private Double donationAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
