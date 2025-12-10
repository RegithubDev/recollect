package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthPermission;

import jakarta.persistence.*;

@Entity
@Table(
    name = ProviderUserPermissions.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"provider_id","permission_id"})
)
public class ProviderUserPermissions {
    public static final String TABLE_NAME = "Backend_provider_user_permissions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;
}
