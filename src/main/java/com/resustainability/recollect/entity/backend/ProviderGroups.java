package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthGroup;
import jakarta.persistence.*;

@Entity
@Table(
    name = ProviderGroups.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"provider_id","group_id"})
)
public class ProviderGroups {
    public static final String TABLE_NAME = "Backend_provider_groups";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;
}
