package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = State.TABLE_NAME)
public class State {
    public static final String TABLE_NAME = "backend_state";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_image", length = 100)
    private String stateImage;

    @Column(name = "state_name", length = 100)
    private String stateName;

    @Column(name = "state_code", length = 100)
    private String stateCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}
