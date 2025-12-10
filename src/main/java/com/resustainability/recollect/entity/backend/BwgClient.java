package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = BwgClient.TABLE_NAME)
public class BwgClient {
    public static final String TABLE_NAME = "Backend_bwgclient";

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

    public BwgClient() {
    }

    public BwgClient(Long id, String fullName, String email, String phoneNumber, Boolean bioOrder, Boolean scrapOrder, String address, Integer familyNumber, Double clientPrice, Double clientCgst, Double clientSgst, String verificationStatus, LocalDateTime dateJoined, Boolean isActive, Boolean isDeleted, District district, State state, Ward ward, String alternateNumber, String clientCategory, String remark, String serviceType, ScrapRegion scrapRegion, String contract, LocalDate contractEndDate, LocalDate contractStartDate, BwgClientRequest approveRequest, Boolean requestApproved, String gstName, String gstNo, String accountNumber, String ifscCode, String password, String plainPassword, String username, String collectionFrequency, Boolean monthlyContract) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bioOrder = bioOrder;
        this.scrapOrder = scrapOrder;
        this.address = address;
        this.familyNumber = familyNumber;
        this.clientPrice = clientPrice;
        this.clientCgst = clientCgst;
        this.clientSgst = clientSgst;
        this.verificationStatus = verificationStatus;
        this.dateJoined = dateJoined;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.district = district;
        this.state = state;
        this.ward = ward;
        this.alternateNumber = alternateNumber;
        this.clientCategory = clientCategory;
        this.remark = remark;
        this.serviceType = serviceType;
        this.scrapRegion = scrapRegion;
        this.contract = contract;
        this.contractEndDate = contractEndDate;
        this.contractStartDate = contractStartDate;
        this.approveRequest = approveRequest;
        this.requestApproved = requestApproved;
        this.gstName = gstName;
        this.gstNo = gstNo;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.password = password;
        this.plainPassword = plainPassword;
        this.username = username;
        this.collectionFrequency = collectionFrequency;
        this.monthlyContract = monthlyContract;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BwgClient entity = (BwgClient) object;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getBioOrder() {
        return bioOrder;
    }

    public void setBioOrder(Boolean bioOrder) {
        this.bioOrder = bioOrder;
    }

    public Boolean getScrapOrder() {
        return scrapOrder;
    }

    public void setScrapOrder(Boolean scrapOrder) {
        this.scrapOrder = scrapOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFamilyNumber() {
        return familyNumber;
    }

    public void setFamilyNumber(Integer familyNumber) {
        this.familyNumber = familyNumber;
    }

    public Double getClientPrice() {
        return clientPrice;
    }

    public void setClientPrice(Double clientPrice) {
        this.clientPrice = clientPrice;
    }

    public Double getClientCgst() {
        return clientCgst;
    }

    public void setClientCgst(Double clientCgst) {
        this.clientCgst = clientCgst;
    }

    public Double getClientSgst() {
        return clientSgst;
    }

    public void setClientSgst(Double clientSgst) {
        this.clientSgst = clientSgst;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ScrapRegion getScrapRegion() {
        return scrapRegion;
    }

    public void setScrapRegion(ScrapRegion scrapRegion) {
        this.scrapRegion = scrapRegion;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public BwgClientRequest getApproveRequest() {
        return approveRequest;
    }

    public void setApproveRequest(BwgClientRequest approveRequest) {
        this.approveRequest = approveRequest;
    }

    public Boolean getRequestApproved() {
        return requestApproved;
    }

    public void setRequestApproved(Boolean requestApproved) {
        this.requestApproved = requestApproved;
    }

    public String getGstName() {
        return gstName;
    }

    public void setGstName(String gstName) {
        this.gstName = gstName;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollectionFrequency() {
        return collectionFrequency;
    }

    public void setCollectionFrequency(String collectionFrequency) {
        this.collectionFrequency = collectionFrequency;
    }

    public Boolean getMonthlyContract() {
        return monthlyContract;
    }

    public void setMonthlyContract(Boolean monthlyContract) {
        this.monthlyContract = monthlyContract;
    }
}
