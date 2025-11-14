package com.resustainability.aakri.entity.django;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = DjangoMigrations.TABLE_NAME)
public class DjangoMigrations {
    public static final String TABLE_NAME = "django_migrations";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app", nullable = false, length = 255)
    private String app;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "applied", nullable = false)
    private LocalDateTime applied;

    public DjangoMigrations() {
    }

    public DjangoMigrations(Long id, String app, String name, LocalDateTime applied) {
        this.id = id;
        this.app = app;
        this.name = name;
        this.applied = applied;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getApplied() {
        return applied;
    }

    public void setApplied(LocalDateTime applied) {
        this.applied = applied;
    }
}
