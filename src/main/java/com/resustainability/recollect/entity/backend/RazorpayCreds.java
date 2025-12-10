package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = RazorpayCreds.TABLE_NAME)
public class RazorpayCreds {
    public static final String TABLE_NAME = "Backend_razorpaycreds";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_id", length = 100)
    private String keyId;

    @Column(name = "is_kerala", nullable = false)
    private Boolean isKerala;
}
