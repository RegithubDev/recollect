package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "localbody_type_id", nullable = false)
    private LocalBodyType localBodyType;

    @Column(name = "is_inclusive_commercial", nullable = false)
    private Boolean isInclusiveCommercial;

    @Column(name = "is_inclusive_residential", nullable = false)
    private Boolean isInclusiveResidential;
}
