package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = CustomerEnquiry.TABLE_NAME)
public class CustomerEnquiry {
    public static final String TABLE_NAME = "backend_customerenquiry";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source", nullable = false, length = 100)
    private String source;

    @Column(name = "cust_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "cust_email", nullable = false, length = 254)
    private String customerEmail;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "subject", length = 100)
    private String subject;

    @Column(name = "message", nullable = false, columnDefinition = "LONGTEXT")
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
