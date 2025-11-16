package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ProviderTeamAllotedWards.TABLE_NAME)
public class ProviderTeamAllotedWards {
    public static final String TABLE_NAME = "backend_providerteamallotedwards";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monday", nullable = false)
    private Boolean monday;

    @Column(name = "tuesday", nullable = false)
    private Boolean tuesday;

    @Column(name = "wednesday", nullable = false)
    private Boolean wednesday;

    @Column(name = "thursday", nullable = false)
    private Boolean thursday;

    @Column(name = "friday", nullable = false)
    private Boolean friday;

    @Column(name = "saturday", nullable = false)
    private Boolean saturday;

    @Column(name = "sunday", nullable = false)
    private Boolean sunday;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private ProviderTeam team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;
}
