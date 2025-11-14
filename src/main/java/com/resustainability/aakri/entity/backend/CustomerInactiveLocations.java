package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = CustomerInactiveLocations.TABLE_NAME)
public class CustomerInactiveLocations {
    public static final String TABLE_NAME = "backend_customerinactivelocations";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "is_scrap", nullable = false)
    private Boolean isScrap;

    @Column(name = "is_bio", nullable = false)
    private Boolean isBio;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
