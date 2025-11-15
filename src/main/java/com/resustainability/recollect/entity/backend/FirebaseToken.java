package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = FirebaseToken.TABLE_NAME)
public class FirebaseToken {
    public static final String TABLE_NAME = "backend_firebasetoken";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, columnDefinition = "LONGTEXT")
    private String token;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
