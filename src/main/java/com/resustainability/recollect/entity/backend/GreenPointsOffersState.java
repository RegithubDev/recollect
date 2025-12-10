package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = GreenPointsOffersState.TABLE_NAME)
public class GreenPointsOffersState {
    public static final String TABLE_NAME = "Backend_greenpointsoffersstate";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private GreenPointOffers offer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
