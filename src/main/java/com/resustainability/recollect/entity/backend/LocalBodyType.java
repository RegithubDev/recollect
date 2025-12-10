package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = LocalBodyType.TABLE_NAME)
public class LocalBodyType {
    public static final String TABLE_NAME = "backend_localbodytype";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "localbody_type", length = 100)
    private String localBodyType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    public LocalBodyType() {
    }

    public LocalBodyType(Long id, String localBodyType, Boolean isActive, Boolean isDeleted) {
        this.id = id;
        this.localBodyType = localBodyType;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LocalBodyType entity = (LocalBodyType) object;
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

    public String getLocalBodyType() {
        return localBodyType;
    }

    public void setLocalBodyType(String localBodyType) {
        this.localBodyType = localBodyType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
