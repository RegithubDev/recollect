package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
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

    public BwgClientRequest() {
    }

    public BwgClientRequest(Long id, String fullName, String phoneNumber, String serviceType, String address, String landmark, String alternateNumber, String clientCategory, LocalDate appointmentDate, String remark, String verificationStatus, State state, Boolean bioPickup, Boolean scrapPickup, String houseName, String houseNumber, LocalDateTime createdAt, String accountNumber, String ifscCode, String collectionFrequency, Integer familyNumber, Boolean isConfirmed, String callCenterRemark) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.serviceType = serviceType;
        this.address = address;
        this.landmark = landmark;
        this.alternateNumber = alternateNumber;
        this.clientCategory = clientCategory;
        this.appointmentDate = appointmentDate;
        this.remark = remark;
        this.verificationStatus = verificationStatus;
        this.state = state;
        this.bioPickup = bioPickup;
        this.scrapPickup = scrapPickup;
        this.houseName = houseName;
        this.houseNumber = houseNumber;
        this.createdAt = createdAt;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.collectionFrequency = collectionFrequency;
        this.familyNumber = familyNumber;
        this.isConfirmed = isConfirmed;
        this.callCenterRemark = callCenterRemark;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BwgClientRequest entity = (BwgClientRequest) object;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getClientCategory() {
        return clientCategory;
    }

    public void setClientCategory(String clientCategory) {
        this.clientCategory = clientCategory;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getBioPickup() {
        return bioPickup;
    }

    public void setBioPickup(Boolean bioPickup) {
        this.bioPickup = bioPickup;
    }

    public Boolean getScrapPickup() {
        return scrapPickup;
    }

    public void setScrapPickup(Boolean scrapPickup) {
        this.scrapPickup = scrapPickup;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCollectionFrequency() {
        return collectionFrequency;
    }

    public void setCollectionFrequency(String collectionFrequency) {
        this.collectionFrequency = collectionFrequency;
    }

    public Integer getFamilyNumber() {
        return familyNumber;
    }

    public void setFamilyNumber(Integer familyNumber) {
        this.familyNumber = familyNumber;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getCallCenterRemark() {
        return callCenterRemark;
    }

    public void setCallCenterRemark(String callCenterRemark) {
        this.callCenterRemark = callCenterRemark;
    }
}
