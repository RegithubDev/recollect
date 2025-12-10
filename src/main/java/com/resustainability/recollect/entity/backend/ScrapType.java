package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = ScrapType.TABLE_NAME)
public class ScrapType {
    public static final String TABLE_NAME = "backend_scraptype";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scrap_name", length = 100)
    private String scrapName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_payable", nullable = false)
    private Boolean isPayable;

    @Column(name = "is_kg", nullable = false)
    private Boolean isKg;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_category_id", nullable = false)
    private ScrapCategory scrapCategory;

    public ScrapType() {
    }

    public ScrapType(Long id, String scrapName, String image, Boolean isPayable, Boolean isKg, Boolean isActive, ScrapCategory scrapCategory) {
        this.id = id;
        this.scrapName = scrapName;
        this.image = image;
        this.isPayable = isPayable;
        this.isKg = isKg;
        this.isActive = isActive;
        this.scrapCategory = scrapCategory;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ScrapType entity = (ScrapType) object;
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

    public String getScrapName() {
        return scrapName;
    }

    public void setScrapName(String scrapName) {
        this.scrapName = scrapName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getPayable() {
        return isPayable;
    }

    public void setPayable(Boolean payable) {
        isPayable = payable;
    }

    public Boolean getKg() {
        return isKg;
    }

    public void setKg(Boolean kg) {
        isKg = kg;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public ScrapCategory getScrapCategory() {
        return scrapCategory;
    }

    public void setScrapCategory(ScrapCategory scrapCategory) {
        this.scrapCategory = scrapCategory;
    }
}
