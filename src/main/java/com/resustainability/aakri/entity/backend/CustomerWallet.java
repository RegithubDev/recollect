package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = CustomerWallet.TABLE_NAME)
public class CustomerWallet {
    public static final String TABLE_NAME = "backend_customerwallet";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
