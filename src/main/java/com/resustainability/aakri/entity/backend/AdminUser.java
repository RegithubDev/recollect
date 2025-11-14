package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = AdminUser.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"phone_number"})
    }
)
public class AdminUser {
    public static final String TABLE_NAME = "backend_adminuser";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_superuser", nullable = false)
    private Boolean isSuperuser;

    @Column(name = "username", nullable = false, length = 150)
    private String username;

    @Column(name = "is_staff", nullable = false)
    private Boolean isStaff;

    @Column(name = "date_joined", nullable = false)
    private LocalDateTime dateJoined;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 254)
    private String email;

    @Column(name = "plain_password", length = 20)
    private String plainPassword;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "admin_role_id")
    private AdminRole adminRole;
}
