package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = BwgClient.TABLE_NAME)
public class BwgClient {
    public static final String TABLE_NAME = "backend_bwgclient";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 300)
    private String fullName;

    @Column(name = "email", length = 254)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "bio_order", nullable = false)
    private Boolean bioOrder;

    @Column(name = "scrap_order", nullable = false)
    private Boolean scrapOrder;

    @Column(name = "address", columnDefinition = "LONGTEXT")
    private String address;

    @Column(name = "family_number", nullable = false)
    private Integer familyNumber;

    @Column(name = "client_price", nullable = false)
    private Double clientPrice;

    @Column(name = "client_cgst", nullable = false)
    private Double clientCgst;

    @Column(name = "client_sgst", nullable = false)
    private Double clientSgst;

    @Column(name = "verification_status", nullable = false, length = 10)
    private String verificationStatus;

    @Column(name = "date_joined", nullable = false)
    private LocalDateTime dateJoined;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @Column(name = "alternate_number", length = 20)
    private String alternateNumber;

    @Column(name = "client_category", length = 100)
    private String clientCategory;

    @Column(name = "remark", columnDefinition = "LONGTEXT")
    private String remark;

    @Column(name = "service_type", columnDefinition = "json")
    private String serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_region_id")
    private ScrapRegion scrapRegion;

    @Column(name = "contract", length = 100)
    private String contract;

    @Column(name = "contract_end_date")
    private LocalDate contractEndDate;

    @Column(name = "contract_start_date")
    private LocalDate contractStartDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approve_request_id")
    private BwgClientRequest approveRequest;

    @Column(name = "request_approved", nullable = false)
    private Boolean requestApproved;

    @Column(name = "gst_name", length = 500)
    private String gstName;

    @Column(name = "gst_no", length = 100)
    private String gstNo;

    @Column(name = "account_number", length = 100)
    private String accountNumber;

    @Column(name = "ifsc_code", length = 100)
    private String ifscCode;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "plain_password", length = 100)
    private String plainPassword;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "collection_frequency", nullable = false, length = 10)
    private String collectionFrequency;

    @Column(name = "monthly_contract", nullable = false)
    private Boolean monthlyContract;
}
