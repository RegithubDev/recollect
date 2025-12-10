package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminSubModule.TABLE_NAME)
public class AdminSubModule {
    public static final String TABLE_NAME = "backend_adminsubmodule";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submodule", nullable = false, length = 255)
    private String submodule;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "role_active", nullable = false)
    private Boolean roleActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private AdminRole role;
}
