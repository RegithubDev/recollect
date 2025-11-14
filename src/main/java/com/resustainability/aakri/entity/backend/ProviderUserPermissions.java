package com.resustainability.aakri.entity.backend;

import com.resustainability.aakri.entity.auth.AuthPermission;

import jakarta.persistence.*;

@Entity
@Table(
    name = ProviderUserPermissions.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"provider_id","permission_id"})
)
public class ProviderUserPermissions {
    public static final String TABLE_NAME = "backend_provider_user_permissions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;
}
