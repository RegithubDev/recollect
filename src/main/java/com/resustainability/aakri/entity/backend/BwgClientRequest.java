package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = BwgClientRequest.TABLE_NAME)
public class BwgClientRequest {
    public static final String TABLE_NAME = "backend_bwgclientrequest";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 300)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "service_type", columnDefinition = "json", nullable = false)
    private String serviceType;

    @Column(name = "address", columnDefinition = "LONGTEXT")
    private String address;

    @Column(name = "landmark", length = 100)
    private String landmark;

    @Column(name = "alternate_number", length = 20)
    private String alternateNumber;

    @Column(name = "client_category", length = 100)
    private String clientCategory;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "remark", columnDefinition = "LONGTEXT")
    private String remark;

    @Column(name = "verification_status", nullable = false, length = 10)
    private String verificationStatus;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "bio_pickup", nullable = false)
    private Boolean bioPickup;

    @Column(name = "scrap_pickup", nullable = false)
    private Boolean scrapPickup;

    @Column(name = "house_name", length = 100)
    private String houseName;

    @Column(name = "house_number", length = 100)
    private String houseNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "account_number", length = 100)
    private String accountNumber;

    @Column(name = "ifsc_code", length = 100)
    private String ifscCode;

    @Column(name = "collection_frequency", nullable = false, length = 10)
    private String collectionFrequency;

    @Column(name = "family_number", nullable = false)
    private Integer familyNumber;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "call_center_remark", columnDefinition = "LONGTEXT")
    private String callCenterRemark;
}
