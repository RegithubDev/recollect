package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = ScrapCategory.TABLE_NAME)
public class ScrapCategory {
    public static final String TABLE_NAME = "Backend_scrapcategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "subcategory_name", nullable = false, length = 10)
    private String subcategoryName;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "hsn_code", length = 10)
    private String hsnCode;

    public ScrapCategory() {
    }

    public ScrapCategory(Long id, String categoryName, String subcategoryName, String image, Boolean isActive, String hsnCode) {
        this.id = id;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.image = image;
        this.isActive = isActive;
        this.hsnCode = hsnCode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ScrapCategory entity = (ScrapCategory) object;
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

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
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

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }
}
