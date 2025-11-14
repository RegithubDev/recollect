package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
