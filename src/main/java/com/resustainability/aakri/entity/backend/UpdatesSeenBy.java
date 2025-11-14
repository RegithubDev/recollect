package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = UpdatesSeenBy.TABLE_NAME)
public class UpdatesSeenBy {
    public static final String TABLE_NAME = "backend_updates_seen_by";

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
