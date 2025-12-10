package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthPermission;

import jakarta.persistence.*;

@Entity
@Table(
    name = LsgdUserPermissions.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"lsgduser_id","permission_id"})
)
public class LsgdUserPermissions {
    public static final String TABLE_NAME = "backend_lsgduser_user_permissions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lsgduser_id", nullable = false)
    private LsgdUser lsgdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;
}
