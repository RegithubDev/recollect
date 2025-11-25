package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private ProviderRoles role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "token_at")
    private LocalDateTime tokenAt;

    public Provider() {
    }

    public Provider(Long id, String password, LocalDateTime lastLogin, Boolean isSuperuser, Boolean isStaff, String providerCode, String fullName, String phoneNumber, String email, String gender, LocalDate dateOfBirth, LocalDateTime dateJoined, String idType, String idNumber, String address, String pincode, Boolean scrapPickup, Boolean biowastePickup, Boolean bwgBioPickup, Boolean bwgScrapPickup, Integer orderPickupLimit, Integer activeOrderCount, String profilePic, String document, Double scrapCashInHand, Double bioCashInHand, Double bwgScrapCashInHand, Double bwgBioCashInHand, Double totalCashInHand, String plainPassword, Boolean logout, Boolean isCashAllowed, Boolean isActive, Boolean isDeleted, ProviderRoles role, State state, LocalDateTime tokenAt) {
        this.id = id;
        this.password = password;
        this.lastLogin = lastLogin;
        this.isSuperuser = isSuperuser;
        this.isStaff = isStaff;
        this.providerCode = providerCode;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateJoined = dateJoined;
        this.idType = idType;
        this.idNumber = idNumber;
        this.address = address;
        this.pincode = pincode;
        this.scrapPickup = scrapPickup;
        this.biowastePickup = biowastePickup;
        this.bwgBioPickup = bwgBioPickup;
        this.bwgScrapPickup = bwgScrapPickup;
        this.orderPickupLimit = orderPickupLimit;
        this.activeOrderCount = activeOrderCount;
        this.profilePic = profilePic;
        this.document = document;
        this.scrapCashInHand = scrapCashInHand;
        this.bioCashInHand = bioCashInHand;
        this.bwgScrapCashInHand = bwgScrapCashInHand;
        this.bwgBioCashInHand = bwgBioCashInHand;
        this.totalCashInHand = totalCashInHand;
        this.plainPassword = plainPassword;
        this.logout = logout;
        this.isCashAllowed = isCashAllowed;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.role = role;
        this.state = state;
        this.tokenAt = tokenAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Provider entity = (Provider) object;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getSuperuser() {
        return isSuperuser;
    }

    public void setSuperuser(Boolean superuser) {
        isSuperuser = superuser;
    }

    public Boolean getStaff() {
        return isStaff;
    }

    public void setStaff(Boolean staff) {
        isStaff = staff;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Boolean getScrapPickup() {
        return scrapPickup;
    }

    public void setScrapPickup(Boolean scrapPickup) {
        this.scrapPickup = scrapPickup;
    }

    public Boolean getBiowastePickup() {
        return biowastePickup;
    }

    public void setBiowastePickup(Boolean biowastePickup) {
        this.biowastePickup = biowastePickup;
    }

    public Boolean getBwgBioPickup() {
        return bwgBioPickup;
    }

    public void setBwgBioPickup(Boolean bwgBioPickup) {
        this.bwgBioPickup = bwgBioPickup;
    }

    public Boolean getBwgScrapPickup() {
        return bwgScrapPickup;
    }

    public void setBwgScrapPickup(Boolean bwgScrapPickup) {
        this.bwgScrapPickup = bwgScrapPickup;
    }

    public Integer getOrderPickupLimit() {
        return orderPickupLimit;
    }

    public void setOrderPickupLimit(Integer orderPickupLimit) {
        this.orderPickupLimit = orderPickupLimit;
    }

    public Integer getActiveOrderCount() {
        return activeOrderCount;
    }

    public void setActiveOrderCount(Integer activeOrderCount) {
        this.activeOrderCount = activeOrderCount;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Double getScrapCashInHand() {
        return scrapCashInHand;
    }

    public void setScrapCashInHand(Double scrapCashInHand) {
        this.scrapCashInHand = scrapCashInHand;
    }

    public Double getBioCashInHand() {
        return bioCashInHand;
    }

    public void setBioCashInHand(Double bioCashInHand) {
        this.bioCashInHand = bioCashInHand;
    }

    public Double getBwgScrapCashInHand() {
        return bwgScrapCashInHand;
    }

    public void setBwgScrapCashInHand(Double bwgScrapCashInHand) {
        this.bwgScrapCashInHand = bwgScrapCashInHand;
    }

    public Double getBwgBioCashInHand() {
        return bwgBioCashInHand;
    }

    public void setBwgBioCashInHand(Double bwgBioCashInHand) {
        this.bwgBioCashInHand = bwgBioCashInHand;
    }

    public Double getTotalCashInHand() {
        return totalCashInHand;
    }

    public void setTotalCashInHand(Double totalCashInHand) {
        this.totalCashInHand = totalCashInHand;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public Boolean getLogout() {
        return logout;
    }

    public void setLogout(Boolean logout) {
        this.logout = logout;
    }

    public Boolean getCashAllowed() {
        return isCashAllowed;
    }

    public void setCashAllowed(Boolean cashAllowed) {
        isCashAllowed = cashAllowed;
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

    public ProviderRoles getRole() {
        return role;
    }

    public void setRole(ProviderRoles role) {
        this.role = role;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getTokenAt() {
        return tokenAt;
    }

    public void setTokenAt(LocalDateTime tokenAt) {
        this.tokenAt = tokenAt;
    }
}
