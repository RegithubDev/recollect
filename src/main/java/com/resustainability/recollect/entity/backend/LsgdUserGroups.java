package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthGroup;

import jakarta.persistence.*;

@Entity
@Table(
    name = LsgdUserGroups.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"lsgduser_id","group_id"})
)
public class LsgdUserGroups {
    public static final String TABLE_NAME = "backend_lsgduser_groups";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lsgduser_id", nullable = false)
    private LsgdUser lsgdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;
}
