package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = CustomerAddress.TABLE_NAME)
public class CustomerAddress {
    public static final String TABLE_NAME = "Backend_customeraddress";

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

    public CustomerAddress() {
    }

    public CustomerAddress(Long id, Boolean scrapService, Boolean scrapLocationActive, Boolean bioWasteService, Boolean bioWasteLocationActive, String residenceType, String residenceDetails, String landmark, String latitude, String longitude, Boolean isDeleted, ScrapRegion scrapRegion, Ward ward, Customer customer) {
        this.id = id;
        this.scrapService = scrapService;
        this.scrapLocationActive = scrapLocationActive;
        this.bioWasteService = bioWasteService;
        this.bioWasteLocationActive = bioWasteLocationActive;
        this.residenceType = residenceType;
        this.residenceDetails = residenceDetails;
        this.landmark = landmark;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isDeleted = isDeleted;
        this.scrapRegion = scrapRegion;
        this.ward = ward;
        this.customer = customer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CustomerAddress entity = (CustomerAddress) object;
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

    public Boolean getScrapService() {
        return scrapService;
    }

    public void setScrapService(Boolean scrapService) {
        this.scrapService = scrapService;
    }

    public Boolean getScrapLocationActive() {
        return scrapLocationActive;
    }

    public void setScrapLocationActive(Boolean scrapLocationActive) {
        this.scrapLocationActive = scrapLocationActive;
    }

    public Boolean getBioWasteService() {
        return bioWasteService;
    }

    public void setBioWasteService(Boolean bioWasteService) {
        this.bioWasteService = bioWasteService;
    }

    public Boolean getBioWasteLocationActive() {
        return bioWasteLocationActive;
    }

    public void setBioWasteLocationActive(Boolean bioWasteLocationActive) {
        this.bioWasteLocationActive = bioWasteLocationActive;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public String getResidenceDetails() {
        return residenceDetails;
    }

    public void setResidenceDetails(String residenceDetails) {
        this.residenceDetails = residenceDetails;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public ScrapRegion getScrapRegion() {
        return scrapRegion;
    }

    public void setScrapRegion(ScrapRegion scrapRegion) {
        this.scrapRegion = scrapRegion;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
