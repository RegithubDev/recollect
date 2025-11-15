package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = LsgdUser.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
public class LsgdUser {
    public static final String TABLE_NAME = "backend_lsgduser";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_superuser", nullable = false)
    private Boolean isSuperuser;

    @Column(name = "is_staff", nullable = false)
    private Boolean isStaff;

    @Column(name = "date_joined", nullable = false)
    private LocalDateTime dateJoined;

    @Column(name = "username", nullable = false, length = 250)
    private String username;

    @Column(name = "lsgd_type", length = 100)
    private String lsgdType;

    @Column(name = "plain_password", length = 20)
    private String plainPassword;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "email", length = 254)
    private String email;

    @ManyToOne
    @JoinColumn(name = "lsgd_role_id")
    private AdminRole adminRole;
}
