package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = BwgOrderUsedBag.TABLE_NAME)
public class BwgOrderUsedBag {
    public static final String TABLE_NAME = "backend_bwgorderusedbag";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_bags", nullable = false)
    private Integer numberOfBags;

    @Column(name = "total_bag_price", nullable = false)
    private Double totalBagPrice;

    @Column(name = "cgst_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal cgstPrice;

    @Column(name = "sgst_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sgstPrice;

    @Column(name = "final_price", nullable = false)
    private Double finalPrice;

    @Column(name = "bag_date")
    private LocalDateTime bagDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bag_id", nullable = false)
    private BwgBagPrice bag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private CompleteOrders order;
    
    public BwgOrderUsedBag()
    {
    	
    }

	public BwgOrderUsedBag(Long id, Integer numberOfBags, Double totalBagPrice, BigDecimal cgstPrice,
			BigDecimal sgstPrice, Double finalPrice, LocalDateTime bagDate, Boolean isDeleted, BwgBagPrice bag,
			CompleteOrders order) {
		super();
		this.id = id;
		this.numberOfBags = numberOfBags;
		this.totalBagPrice = totalBagPrice;
		this.cgstPrice = cgstPrice;
		this.sgstPrice = sgstPrice;
		this.finalPrice = finalPrice;
		this.bagDate = bagDate;
		this.isDeleted = isDeleted;
		this.bag = bag;
		this.order = order;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumberOfBags() {
		return numberOfBags;
	}

	public void setNumberOfBags(Integer numberOfBags) {
		this.numberOfBags = numberOfBags;
	}

	public Double getTotalBagPrice() {
		return totalBagPrice;
	}

	public void setTotalBagPrice(Double totalBagPrice) {
		this.totalBagPrice = totalBagPrice;
	}

	public BigDecimal getCgstPrice() {
		return cgstPrice;
	}

	public void setCgstPrice(BigDecimal cgstPrice) {
		this.cgstPrice = cgstPrice;
	}

	public BigDecimal getSgstPrice() {
		return sgstPrice;
	}

	public void setSgstPrice(BigDecimal sgstPrice) {
		this.sgstPrice = sgstPrice;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public LocalDateTime getBagDate() {
		return bagDate;
	}

	public void setBagDate(LocalDateTime bagDate) {
		this.bagDate = bagDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public BwgBagPrice getBag() {
		return bag;
	}

	public void setBag(BwgBagPrice bag) {
		this.bag = bag;
	}

	public CompleteOrders getOrder() {
		return order;
	}

	public void setOrder(CompleteOrders order) {
		this.order = order;
	}
    
    
    
}
