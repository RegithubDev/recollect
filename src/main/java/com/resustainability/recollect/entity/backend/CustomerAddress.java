package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = CustomerAddress.TABLE_NAME)
public class CustomerAddress {
    public static final String TABLE_NAME = "backend_customeraddress";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_service", nullable = false)
    private Boolean scrapService;

    @Column(name = "scrap_location_active", nullable = false)
    private Boolean scrapLocationActive;

    @Column(name = "biowaste_service", nullable = false)
    private Boolean bioWasteService;

    @Column(name = "biowaste_location_active", nullable = false)
    private Boolean bioWasteLocationActive;

    @Column(name = "residence_type", length = 20)
    private String residenceType;

    @Column(name = "residence_details", length = 500)
    private String residenceDetails;

    @Column(name = "landmark", length = 500)
    private String landmark;

    @Column(name = "latitude", nullable = false, length = 100)
    private String latitude;

    @Column(name = "longitude", nullable = false, length = 100)
    private String longitude;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_region_id")
    private ScrapRegion scrapRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
