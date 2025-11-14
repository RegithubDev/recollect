package com.resustainability.aakri.entity.backend;

import com.resustainability.aakri.entity.auth.AuthGroup;

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

    @ManyToOne
    @JoinColumn(name = "lsgduser_id", nullable = false)
    private LsgdUser lsgdUser;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;
}
