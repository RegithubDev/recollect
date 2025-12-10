package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminSubSection.TABLE_NAME)
public class AdminSubSection {
    public static final String TABLE_NAME = "Backend_adminsubsection";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subsection", nullable = false, length = 500)
    private String subsection;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "role_active", nullable = false)
    private Boolean roleActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private AdminRole role;
}
