package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminRole.TABLE_NAME)
public class AdminRole {
    public static final String TABLE_NAME = "backend_adminrole";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, length = 500)
    private String roleName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
