package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ProviderTeamMember.TABLE_NAME)
public class ProviderTeamMember {
    public static final String TABLE_NAME = "Backend_providerteammember";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private ProviderTeam team;
}
