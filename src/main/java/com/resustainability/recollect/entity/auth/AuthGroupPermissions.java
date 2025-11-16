package com.resustainability.recollect.entity.auth;

import jakarta.persistence.*;

@Entity
@Table(
        name = AuthGroupPermissions.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"group_id", "permission_id"})
        }
)
public class AuthGroupPermissions {
    public static final String TABLE_NAME = "auth_group_permissions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;

    public AuthGroupPermissions() {}

    public AuthGroupPermissions(Long id, AuthGroup group, AuthPermission permission) {
        this.id = id;
        this.group = group;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthGroup getGroup() {
        return group;
    }

    public void setGroup(AuthGroup group) {
        this.group = group;
    }

    public AuthPermission getPermission() {
        return permission;
    }

    public void setPermission(AuthPermission permission) {
        this.permission = permission;
    }
}
