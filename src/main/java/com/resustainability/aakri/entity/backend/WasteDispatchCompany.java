package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = WasteDispatchCompany.TABLE_NAME)
public class WasteDispatchCompany {
    public static final String TABLE_NAME = "backend_wastedispatchcompany";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "company_short_name", length = 100)
    private String companyShortName;
}
