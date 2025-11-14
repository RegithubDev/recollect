package com.resustainability.aakri.entity.backend;

import com.resustainability.aakri.entity.auth.AuthGroup;

import jakarta.persistence.*;

@Entity
@Table(
    name = CustomerGroups.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "group_id"})
    }
)
public class CustomerGroups {
    public static final String TABLE_NAME = "backend_customer_groups";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;
}
