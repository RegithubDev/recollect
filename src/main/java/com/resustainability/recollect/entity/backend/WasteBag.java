package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = WasteBag.TABLE_NAME)
public class WasteBag {
    public static final String TABLE_NAME = "Backend_wastebag";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bag_size", nullable = false, length = 20)
    private String bagSize;

    @Column(name = "bag_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal bagPrice;

    @Column(name = "bag_cgst", nullable = false)
    private Double bagCgst;

    @Column(name = "bag_sgst", nullable = false)
    private Double bagSgst;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "is_bwg", nullable = false)
    private Boolean isBwg;

	public WasteBag(Long id, String bagSize, BigDecimal bagPrice, Double bagCgst, Double bagSgst, Boolean isActive,
			State state, Boolean isBwg) {
		super();
		this.id = id;
		this.bagSize = bagSize;
		this.bagPrice = bagPrice;
		this.bagCgst = bagCgst;
		this.bagSgst = bagSgst;
		this.isActive = isActive;
		this.state = state;
		this.isBwg = isBwg;
	}
    
    public WasteBag() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBagSize() {
		return bagSize;
	}

	public void setBagSize(String bagSize) {
		this.bagSize = bagSize;
	}

	public BigDecimal getBagPrice() {
		return bagPrice;
	}

	public void setBagPrice(BigDecimal bagPrice) {
		this.bagPrice = bagPrice;
	}

	public Double getBagCgst() {
		return bagCgst;
	}

	public void setBagCgst(Double bagCgst) {
		this.bagCgst = bagCgst;
	}

	public Double getBagSgst() {
		return bagSgst;
	}

	public void setBagSgst(Double bagSgst) {
		this.bagSgst = bagSgst;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Boolean getIsBwg() {
		return isBwg;
	}

	public void setIsBwg(Boolean isBwg) {
		this.isBwg = isBwg;
	}
    
    
}
