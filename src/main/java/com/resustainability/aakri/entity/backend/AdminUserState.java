package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminUserState.TABLE_NAME)
public class AdminUserState {
    public static final String TABLE_NAME = "backend_adminuserstate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_user_id", nullable = false)
    private AdminUser adminUser;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
