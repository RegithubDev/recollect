package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AakriContactNumber.TABLE_NAME)
public class AakriContactNumber {
    public static final String TABLE_NAME = "backend_aakricontactnumber";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "is_kerala", nullable = false)
    private Boolean isKerala;
}
