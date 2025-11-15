package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

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
}
