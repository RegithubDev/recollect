package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = ScrapTypeLocationAndPrice.TABLE_NAME)
public class ScrapTypeLocationAndPrice {
    public static final String TABLE_NAME = "Backend_scraptypelocationandprice";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_price")
    private Double scrapPrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_type_id", nullable = false)
    private ScrapType scrapType;

    @Column(name = "scrap_cgst")
    private Double scrapCgst;

    @Column(name = "scrap_sgst")
    private Double scrapSgst;

    public ScrapTypeLocationAndPrice() {
    }

    public ScrapTypeLocationAndPrice(Long id, Double scrapPrice, Boolean isActive, District district, ScrapType scrapType, Double scrapCgst, Double scrapSgst) {
        this.id = id;
        this.scrapPrice = scrapPrice;
        this.isActive = isActive;
        this.district = district;
        this.scrapType = scrapType;
        this.scrapCgst = scrapCgst;
        this.scrapSgst = scrapSgst;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ScrapTypeLocationAndPrice entity = (ScrapTypeLocationAndPrice) object;
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

    public Double getScrapPrice() {
        return scrapPrice;
    }

    public void setScrapPrice(Double scrapPrice) {
        this.scrapPrice = scrapPrice;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public ScrapType getScrapType() {
        return scrapType;
    }

    public void setScrapType(ScrapType scrapType) {
        this.scrapType = scrapType;
    }

    public Double getScrapCgst() {
        return scrapCgst;
    }

    public void setScrapCgst(Double scrapCgst) {
        this.scrapCgst = scrapCgst;
    }

    public Double getScrapSgst() {
        return scrapSgst;
    }

    public void setScrapSgst(Double scrapSgst) {
        this.scrapSgst = scrapSgst;
    }
}
