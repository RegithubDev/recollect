package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BwgBagPrice.TABLE_NAME)
public class BwgBagPrice {
    public static final String TABLE_NAME = "backend_bwgbagprice";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bag_size", nullable = false, length = 20)
    private String bagSize;

    @Column(name = "bag_price", nullable = false)
    private Double bagPrice;

    @Column(name = "bag_cgst", nullable = false)
    private Double bagCgst;

    @Column(name = "bag_sgst", nullable = false)
    private Double bagSgst;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private BwgClient client;
    
    public BwgBagPrice() {
    	
    }

	public BwgBagPrice(Long id, String bagSize, Double bagPrice, Double bagCgst, Double bagSgst, Boolean isActive,
			BwgClient client) {
		super();
		this.id = id;
		this.bagSize = bagSize;
		this.bagPrice = bagPrice;
		this.bagCgst = bagCgst;
		this.bagSgst = bagSgst;
		this.isActive = isActive;
		this.client = client;
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

	public Double getBagPrice() {
		return bagPrice;
	}

	public void setBagPrice(Double bagPrice) {
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

	public BwgClient getClient() {
		return client;
	}

	public void setClient(BwgClient client) {
		this.client = client;
	}
    
	
    
}
