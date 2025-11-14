package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderLocalBody.TABLE_NAME)
public class ProviderLocalBody {
    public static final String TABLE_NAME = "backend_providerlocalbody";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "localbody_id", nullable = false)
    private LocalBody localBody;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
