package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = GreenPointOffers.TABLE_NAME)
public class GreenPointOffers {
    public static final String TABLE_NAME = "Backend_greenpointoffers";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company", length = 100)
    private String company;

    @Column(name = "company_code", length = 100)
    private String companyCode;

    @Column(name = "offer_percentage", columnDefinition = "LONGTEXT")
    private String offerPercentage;

    @Column(name = "offer_description", columnDefinition = "LONGTEXT")
    private String offerDescription;

    @Column(name = "offer_image", length = 100)
    private String offerImage;

    @Column(name = "min_offer_points", nullable = false)
    private Integer minOfferPoints;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expiry_day", nullable = false)
    private Integer expiryDay;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
