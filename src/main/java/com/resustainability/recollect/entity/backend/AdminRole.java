package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = AdminRole.TABLE_NAME)
public class AdminRole {
    public static final String TABLE_NAME = "Backend_adminrole";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, length = 500)
    private String roleName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public AdminRole() {
    }

    public AdminRole(Long id, String roleName, Boolean isActive) {
        this.id = id;
        this.roleName = roleName;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AdminRole entity = (AdminRole) object;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
