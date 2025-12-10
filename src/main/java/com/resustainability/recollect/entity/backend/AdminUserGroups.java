package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthGroup;

import jakarta.persistence.*;

@Entity
@Table(
    name = AdminUserGroups.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"adminuser_id","group_id"})
    }
)
public class AdminUserGroups {
    public static final String TABLE_NAME = "Backend_adminuser_groups";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminuser_id", nullable = false)
    private AdminUser adminUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;
}
