package com.resustainability.aakri.entity.backend;

import com.resustainability.aakri.entity.auth.AuthGroup;
import jakarta.persistence.*;

@Entity
@Table(
    name = ProviderGroups.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"provider_id","group_id"})
)
public class ProviderGroups {
    public static final String TABLE_NAME = "backend_provider_groups";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;
}
