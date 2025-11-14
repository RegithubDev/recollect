package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = District.TABLE_NAME)
public class District {
    public static final String TABLE_NAME = "backend_district";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_name", length = 100)
    private String districtName;

    @Column(name = "district_code", length = 5)
    private String districtCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
