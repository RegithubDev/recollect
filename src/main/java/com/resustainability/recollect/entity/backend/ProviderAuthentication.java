package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderAuthentication.TABLE_NAME)
public class ProviderAuthentication {
    public static final String TABLE_NAME = "backend_providerauthentication";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp", length = 6)
    private String otp;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
