package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = BioWasteType.TABLE_NAME)
public class BioWasteType {
    public static final String TABLE_NAME = "backend_biowastetype";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biowaste_name", length = 100)
    private String biowasteName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biowaste_category_id", nullable = false)
    private BioWasteCategory biowasteCategory;

    public BioWasteType() {
    }

    public BioWasteType(Long id, String biowasteName, String image, Boolean isActive, BioWasteCategory biowasteCategory) {
        this.id = id;
        this.biowasteName = biowasteName;
        this.image = image;
        this.isActive = isActive;
        this.biowasteCategory = biowasteCategory;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BioWasteType entity = (BioWasteType) object;
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

    public String getBiowasteName() {
        return biowasteName;
    }

    public void setBiowasteName(String biowasteName) {
        this.biowasteName = biowasteName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public BioWasteCategory getBiowasteCategory() {
        return biowasteCategory;
    }

    public void setBiowasteCategory(BioWasteCategory biowasteCategory) {
        this.biowasteCategory = biowasteCategory;
    }
}
