package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = UpdatesSendTo.TABLE_NAME)
public class UpdatesSendTo {
    public static final String TABLE_NAME = "backend_updates_send_to";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "updates_id", nullable = false)
    private Updates updates;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
