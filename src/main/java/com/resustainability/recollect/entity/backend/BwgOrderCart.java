package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = BwgOrderCart.TABLE_NAME)
public class BwgOrderCart {
    public static final String TABLE_NAME = "backend_bwgordercart";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_weight", nullable = false)
    private Double scrapWeight;

    @Column(name = "scrap_price", nullable = false)
    private Double scrapPrice;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biowaste_type_id")
    private BioWasteType bioWasteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwg_order_id", nullable = false)
    private BwgOrders bwgOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_type_id")
    private ScrapType scrapType;

    @Column(name = "scrap_gst", nullable = false)
    private Double scrapGst;

    @Column(name = "scrap_hsn", length = 10)
    private String scrapHsn;
    
    
    
    public BwgOrderCart() {
    	
    }
    

	public BwgOrderCart(Long id, Double scrapWeight, Double scrapPrice, Double totalPrice, Boolean isDeleted,
			BioWasteType bioWasteType, BwgOrders bwgOrder, ScrapType scrapType, Double scrapGst, String scrapHsn) {
		super();
		this.id = id;
		this.scrapWeight = scrapWeight;
		this.scrapPrice = scrapPrice;
		this.totalPrice = totalPrice;
		this.isDeleted = isDeleted;
		this.bioWasteType = bioWasteType;
		this.bwgOrder = bwgOrder;
		this.scrapType = scrapType;
		this.scrapGst = scrapGst;
		this.scrapHsn = scrapHsn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getScrapWeight() {
		return scrapWeight;
	}

	public void setScrapWeight(Double scrapWeight) {
		this.scrapWeight = scrapWeight;
	}

	public Double getScrapPrice() {
		return scrapPrice;
	}

	public void setScrapPrice(Double scrapPrice) {
		this.scrapPrice = scrapPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public BioWasteType getBioWasteType() {
		return bioWasteType;
	}

	public void setBioWasteType(BioWasteType bioWasteType) {
		this.bioWasteType = bioWasteType;
	}

	public BwgOrders getBwgOrder() {
		return bwgOrder;
	}

	public void setBwgOrder(BwgOrders bwgOrder) {
		this.bwgOrder = bwgOrder;
	}

	public ScrapType getScrapType() {
		return scrapType;
	}

	public void setScrapType(ScrapType scrapType) {
		this.scrapType = scrapType;
	}

	public Double getScrapGst() {
		return scrapGst;
	}

	public void setScrapGst(Double scrapGst) {
		this.scrapGst = scrapGst;
	}

	public String getScrapHsn() {
		return scrapHsn;
	}

	public void setScrapHsn(String scrapHsn) {
		this.scrapHsn = scrapHsn;
	}
    
    
    
}
