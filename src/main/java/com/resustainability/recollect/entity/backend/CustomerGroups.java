package com.resustainability.recollect.entity.backend;

import com.resustainability.recollect.entity.auth.AuthGroup;

import jakarta.persistence.*;

@Entity
@Table(
    name = CustomerGroups.TABLE_NAME,
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "group_id"})
    }
)
public class CustomerGroups {
    public static final String TABLE_NAME = "Backend_customer_groups";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private AuthGroup group;
}
