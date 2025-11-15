package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthPermission;

import jakarta.persistence.*;

@Entity
@Table(
    name = CustomerPermissions.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id","permission_id"})
    }
)
public class CustomerPermissions {
    public static final String TABLE_NAME = "backend_customer_user_permissions";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private AuthPermission permission;
}
