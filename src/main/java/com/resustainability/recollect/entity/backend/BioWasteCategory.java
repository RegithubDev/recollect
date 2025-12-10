package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = BioWasteCategory.TABLE_NAME)
public class BioWasteCategory {
    public static final String TABLE_NAME = "Backend_biowastecategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public BioWasteCategory() {
    }

    public BioWasteCategory(Long id, String categoryName, String image, Boolean isActive) {
        this.id = id;
        this.categoryName = categoryName;
        this.image = image;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BioWasteCategory entity = (BioWasteCategory) object;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
}
