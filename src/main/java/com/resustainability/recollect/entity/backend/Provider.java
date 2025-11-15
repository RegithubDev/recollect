package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = Provider.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = "provider_code"),
        @UniqueConstraint(columnNames = "phone_number")
})
public class Provider {
    public static final String TABLE_NAME = "backend_provider";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_superuser", nullable = false)
    private Boolean isSuperuser;

    @Column(name = "is_staff", nullable = false)
    private Boolean isStaff;

    @Column(name = "provider_code", length = 20)
    private String providerCode;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 254)
    private String email;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_joined", nullable = false)
    private LocalDateTime dateJoined;

    @Column(name = "id_type", length = 20)
    private String idType;

    @Column(name = "id_number", length = 20)
    private String idNumber;

    @Column(name = "address", columnDefinition = "LONGTEXT")
    private String address;

    @Column(name = "pincode", length = 6)
    private String pincode;

    @Column(name = "scrap_pickup", nullable = false)
    private Boolean scrapPickup;

    @Column(name = "biowaste_pickup", nullable = false)
    private Boolean biowastePickup;

    @Column(name = "bwg_bio_pickup", nullable = false)
    private Boolean bwgBioPickup;

    @Column(name = "bwg_scrap_pickup", nullable = false)
    private Boolean bwgScrapPickup;

    @Column(name = "order_pickup_limit", nullable = false)
    private Integer orderPickupLimit;

    @Column(name = "active_order_count", nullable = false)
    private Integer activeOrderCount;

    @Column(name = "profile_pic", length = 100)
    private String profilePic;

    @Column(name = "document", length = 100)
    private String document;

    @Column(name = "scrap_cash_in_hand", nullable = false)
    private Double scrapCashInHand;

    @Column(name = "bio_cash_in_hand", nullable = false)
    private Double bioCashInHand;

    @Column(name = "bwg_scrap_cash_in_hand", nullable = false)
    private Double bwgScrapCashInHand;

    @Column(name = "bwg_bio_cash_in_hand", nullable = false)
    private Double bwgBioCashInHand;

    @Column(name = "total_cash_in_hand", nullable = false)
    private Double totalCashInHand;

    @Column(name = "plain_password", length = 20)
    private String plainPassword;

    @Column(name = "logout", nullable = false)
    private Boolean logout;

    @Column(name = "is_cash_allowed", nullable = false)
    private Boolean isCashAllowed;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private ProviderRoles role;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
