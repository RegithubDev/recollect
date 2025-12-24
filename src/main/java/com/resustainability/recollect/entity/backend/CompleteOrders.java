package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = CompleteOrders.TABLE_NAME)
public class CompleteOrders {
    public static final String TABLE_NAME = "backend_completeorders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    @Column(name = "order_type", length = 20)
    private String orderType;

    @Column(name = "order_status", nullable = false, length = 20)
    private String orderStatus;

    @Column(name = "bio_total_bill_amount")
    private Double bioTotalBillAmount;

    @Column(name = "bio_subsidy_amount")
    private Double bioSubsidyAmount;

    @Column(name = "bio_bill_amount")
    private Double bioBillAmount;

    @Column(name = "bio_cgst_amount")
    private Double bioCgstAmount;

    @Column(name = "bio_sgst_amount")
    private Double bioSgstAmount;

    @Column(name = "bio_bag_amount")
    private Double bioBagAmount;

    @Column(name = "bio_total_bill")
    private Double bioTotalBill;

    @Column(name = "bio_wallet_deduct")
    private Double bioWalletDeduct;

    @Column(name = "bio_weight", nullable = false)
    private Double bioWeight;

    @Column(name = "scrap_total_bill")
    private Double scrapTotalBill;

    @Column(name = "scrap_wallet_deduct")
    private Double scrapWalletDeduct;

    @Column(name = "scrap_service_charge")
    private Double scrapServiceCharge;

    @Column(name = "scrap_roundoff")
    private Double scrapRoundoff;

    @Column(name = "final_bill")
    private Double finalBill;

    @Column(name = "payment_method", length = 20)
    private String paymentMethod;

    @Column(name = "is_selected", nullable = false)
    private Boolean isSelected;

    @Column(name = "billed_at")
    private LocalDateTime billedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "cancel_request", nullable = false)
    private Boolean cancelRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biowaste_order_id")
    private BioWasteOrders bioWasteOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwg_order_id")
    private BwgOrders bwgOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private BwgClient client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private PickupVehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_order_id")
    private ScrapOrders scrapOrder;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public CompleteOrders() {
    }

    public CompleteOrders(Long id, LocalDate scheduleDate, String orderType, String orderStatus, Double bioTotalBillAmount, Double bioSubsidyAmount, Double bioBillAmount, Double bioCgstAmount, Double bioSgstAmount, Double bioBagAmount, Double bioTotalBill, Double bioWalletDeduct, Double bioWeight, Double scrapTotalBill, Double scrapWalletDeduct, Double scrapServiceCharge, Double scrapRoundoff, Double finalBill, String paymentMethod, Boolean isSelected, LocalDateTime billedAt, Boolean isDeleted, Boolean cancelRequest, BioWasteOrders bioWasteOrder, BwgOrders bwgOrder, BwgClient client, District district, PickupVehicle vehicle, Provider provider, ScrapOrders scrapOrder, State state, Customer customer) {
        this.id = id;
        this.scheduleDate = scheduleDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.bioTotalBillAmount = bioTotalBillAmount;
        this.bioSubsidyAmount = bioSubsidyAmount;
        this.bioBillAmount = bioBillAmount;
        this.bioCgstAmount = bioCgstAmount;
        this.bioSgstAmount = bioSgstAmount;
        this.bioBagAmount = bioBagAmount;
        this.bioTotalBill = bioTotalBill;
        this.bioWalletDeduct = bioWalletDeduct;
        this.bioWeight = bioWeight;
        this.scrapTotalBill = scrapTotalBill;
        this.scrapWalletDeduct = scrapWalletDeduct;
        this.scrapServiceCharge = scrapServiceCharge;
        this.scrapRoundoff = scrapRoundoff;
        this.finalBill = finalBill;
        this.paymentMethod = paymentMethod;
        this.isSelected = isSelected;
        this.billedAt = billedAt;
        this.isDeleted = isDeleted;
        this.cancelRequest = cancelRequest;
        this.bioWasteOrder = bioWasteOrder;
        this.bwgOrder = bwgOrder;
        this.client = client;
        this.district = district;
        this.vehicle = vehicle;
        this.provider = provider;
        this.scrapOrder = scrapOrder;
        this.state = state;
        this.customer = customer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CompleteOrders entity = (CompleteOrders) object;
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

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getBioTotalBillAmount() {
        return bioTotalBillAmount;
    }

    public void setBioTotalBillAmount(Double bioTotalBillAmount) {
        this.bioTotalBillAmount = bioTotalBillAmount;
    }

    public Double getBioSubsidyAmount() {
        return bioSubsidyAmount;
    }

    public void setBioSubsidyAmount(Double bioSubsidyAmount) {
        this.bioSubsidyAmount = bioSubsidyAmount;
    }

    public Double getBioBillAmount() {
        return bioBillAmount;
    }

    public void setBioBillAmount(Double bioBillAmount) {
        this.bioBillAmount = bioBillAmount;
    }

    public Double getBioCgstAmount() {
        return bioCgstAmount;
    }

    public void setBioCgstAmount(Double bioCgstAmount) {
        this.bioCgstAmount = bioCgstAmount;
    }

    public Double getBioSgstAmount() {
        return bioSgstAmount;
    }

    public void setBioSgstAmount(Double bioSgstAmount) {
        this.bioSgstAmount = bioSgstAmount;
    }

    public Double getBioBagAmount() {
        return bioBagAmount;
    }

    public void setBioBagAmount(Double bioBagAmount) {
        this.bioBagAmount = bioBagAmount;
    }

    public Double getBioTotalBill() {
        return bioTotalBill;
    }

    public void setBioTotalBill(Double bioTotalBill) {
        this.bioTotalBill = bioTotalBill;
    }

    public Double getBioWalletDeduct() {
        return bioWalletDeduct;
    }

    public void setBioWalletDeduct(Double bioWalletDeduct) {
        this.bioWalletDeduct = bioWalletDeduct;
    }

    public Double getBioWeight() {
        return bioWeight;
    }

    public void setBioWeight(Double bioWeight) {
        this.bioWeight = bioWeight;
    }

    public Double getScrapTotalBill() {
        return scrapTotalBill;
    }

    public void setScrapTotalBill(Double scrapTotalBill) {
        this.scrapTotalBill = scrapTotalBill;
    }

    public Double getScrapWalletDeduct() {
        return scrapWalletDeduct;
    }

    public void setScrapWalletDeduct(Double scrapWalletDeduct) {
        this.scrapWalletDeduct = scrapWalletDeduct;
    }

    public Double getScrapServiceCharge() {
        return scrapServiceCharge;
    }

    public void setScrapServiceCharge(Double scrapServiceCharge) {
        this.scrapServiceCharge = scrapServiceCharge;
    }

    public Double getScrapRoundoff() {
        return scrapRoundoff;
    }

    public void setScrapRoundoff(Double scrapRoundoff) {
        this.scrapRoundoff = scrapRoundoff;
    }

    public Double getFinalBill() {
        return finalBill;
    }

    public void setFinalBill(Double finalBill) {
        this.finalBill = finalBill;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public LocalDateTime getBilledAt() {
        return billedAt;
    }

    public void setBilledAt(LocalDateTime billedAt) {
        this.billedAt = billedAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getCancelRequest() {
        return cancelRequest;
    }

    public void setCancelRequest(Boolean cancelRequest) {
        this.cancelRequest = cancelRequest;
    }

    public BioWasteOrders getBioWasteOrder() {
        return bioWasteOrder;
    }

    public void setBioWasteOrder(BioWasteOrders bioWasteOrder) {
        this.bioWasteOrder = bioWasteOrder;
    }

    public BwgOrders getBwgOrder() {
        return bwgOrder;
    }

    public void setBwgOrder(BwgOrders bwgOrder) {
        this.bwgOrder = bwgOrder;
    }

    public BwgClient getClient() {
        return client;
    }

    public void setClient(BwgClient client) {
        this.client = client;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public PickupVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(PickupVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public ScrapOrders getScrapOrder() {
        return scrapOrder;
    }

    public void setScrapOrder(ScrapOrders scrapOrder) {
        this.scrapOrder = scrapOrder;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
