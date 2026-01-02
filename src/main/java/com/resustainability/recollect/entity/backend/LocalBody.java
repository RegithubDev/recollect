package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import org.locationtech.jts.geom.MultiPolygon;

import java.util.Objects;

@Entity
@Table(name = LocalBody.TABLE_NAME)
public class LocalBody {
    public static final String TABLE_NAME = "backend_localbody";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "localbody_name", length = 100)
    private String localBodyName;

    @Column(name = "border_polygon", columnDefinition = "LONGTEXT")
    private String borderPolygon;

    @Column(name = "bio_processing_charge")
    private Double bioProcessingCharge;

    @Column(name = "bio_service_charge")
    private Double bioServiceCharge;

    @Column(name = "bio_subsidy_amount")
    private Double bioSubsidyAmount;

    @Column(name = "bio_cgst_percentage")
    private Double bioCgstPercentage;

    @Column(name = "bio_sgst_percentage")
    private Double bioSgstPercentage;

    @Column(name = "bio_residential_price")
    private Double bioResidentialPrice;

    @Column(name = "bio_commercial_price")
    private Double bioCommercialPrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localbody_type_id", nullable = false)
    private LocalBodyType localBodyType;

    @Column(name = "is_inclusive_commercial", nullable = false)
    private Boolean isInclusiveCommercial;

    @Column(name = "is_inclusive_residential", nullable = false)
    private Boolean isInclusiveResidential;

    @Column(name = "geometry", columnDefinition = "MULTIPOLYGON SRID 4326")
    private MultiPolygon geometry;

    public LocalBody() {
    }

    public LocalBody(Long id, String localBodyName, String borderPolygon, Double bioProcessingCharge, Double bioServiceCharge, Double bioSubsidyAmount, Double bioCgstPercentage, Double bioSgstPercentage, Double bioResidentialPrice, Double bioCommercialPrice, Boolean isActive, Boolean isDeleted, District district, LocalBodyType localBodyType, Boolean isInclusiveCommercial, Boolean isInclusiveResidential, MultiPolygon geometry) {
        this.id = id;
        this.localBodyName = localBodyName;
        this.borderPolygon = borderPolygon;
        this.bioProcessingCharge = bioProcessingCharge;
        this.bioServiceCharge = bioServiceCharge;
        this.bioSubsidyAmount = bioSubsidyAmount;
        this.bioCgstPercentage = bioCgstPercentage;
        this.bioSgstPercentage = bioSgstPercentage;
        this.bioResidentialPrice = bioResidentialPrice;
        this.bioCommercialPrice = bioCommercialPrice;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.district = district;
        this.localBodyType = localBodyType;
        this.isInclusiveCommercial = isInclusiveCommercial;
        this.isInclusiveResidential = isInclusiveResidential;
        this.geometry = geometry;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LocalBody entity = (LocalBody) object;
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

    public String getLocalBodyName() {
        return localBodyName;
    }

    public void setLocalBodyName(String localBodyName) {
        this.localBodyName = localBodyName;
    }

    public String getBorderPolygon() {
        return borderPolygon;
    }

    public void setBorderPolygon(String borderPolygon) {
        this.borderPolygon = borderPolygon;
    }

    public Double getBioProcessingCharge() {
        return bioProcessingCharge;
    }

    public void setBioProcessingCharge(Double bioProcessingCharge) {
        this.bioProcessingCharge = bioProcessingCharge;
    }

    public Double getBioServiceCharge() {
        return bioServiceCharge;
    }

    public void setBioServiceCharge(Double bioServiceCharge) {
        this.bioServiceCharge = bioServiceCharge;
    }

    public Double getBioSubsidyAmount() {
        return bioSubsidyAmount;
    }

    public void setBioSubsidyAmount(Double bioSubsidyAmount) {
        this.bioSubsidyAmount = bioSubsidyAmount;
    }

    public Double getBioCgstPercentage() {
        return bioCgstPercentage;
    }

    public void setBioCgstPercentage(Double bioCgstPercentage) {
        this.bioCgstPercentage = bioCgstPercentage;
    }

    public Double getBioSgstPercentage() {
        return bioSgstPercentage;
    }

    public void setBioSgstPercentage(Double bioSgstPercentage) {
        this.bioSgstPercentage = bioSgstPercentage;
    }

    public Double getBioResidentialPrice() {
        return bioResidentialPrice;
    }

    public void setBioResidentialPrice(Double bioResidentialPrice) {
        this.bioResidentialPrice = bioResidentialPrice;
    }

    public Double getBioCommercialPrice() {
        return bioCommercialPrice;
    }

    public void setBioCommercialPrice(Double bioCommercialPrice) {
        this.bioCommercialPrice = bioCommercialPrice;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public LocalBodyType getLocalBodyType() {
        return localBodyType;
    }

    public void setLocalBodyType(LocalBodyType localBodyType) {
        this.localBodyType = localBodyType;
    }

    public Boolean getInclusiveCommercial() {
        return isInclusiveCommercial;
    }

    public void setInclusiveCommercial(Boolean inclusiveCommercial) {
        isInclusiveCommercial = inclusiveCommercial;
    }

    public Boolean getInclusiveResidential() {
        return isInclusiveResidential;
    }

    public void setInclusiveResidential(Boolean inclusiveResidential) {
        isInclusiveResidential = inclusiveResidential;
    }

    public MultiPolygon getGeometry() {
        return geometry;
    }

    public void setGeometry(MultiPolygon geometry) {
        this.geometry = geometry;
    }
}
