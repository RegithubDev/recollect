package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthPermission;

import jakarta.persistence.*;

@Entity
@Table(
    name = AdminUserPermissions.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"adminuser_id","permission_id"})
    }
)
public class AdminUserPermissions {
    public static final String TABLE_NAME = "Backend_adminuser_user_permissions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminuser_id", nullable = false)
    private AdminUser adminUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;
}
