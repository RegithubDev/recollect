package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminModule.TABLE_NAME)
public class AdminModule {
    public static final String TABLE_NAME = "backend_adminmodule";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module", nullable = false, length = 255)
    private String module;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "role_active", nullable = false)
    private Boolean roleActive;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private AdminRole role;
}
