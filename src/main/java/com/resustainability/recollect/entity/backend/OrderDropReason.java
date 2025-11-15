package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = OrderDropReason.TABLE_NAME)
public class OrderDropReason {
    public static final String TABLE_NAME = "backend_orderdropreason";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reason", nullable = false, columnDefinition = "LONGTEXT")
    private String reason;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
