package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminUserState.TABLE_NAME)
public class AdminUserState {
    public static final String TABLE_NAME = "backend_adminuserstate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", nullable = false)
    private AdminUser adminUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
