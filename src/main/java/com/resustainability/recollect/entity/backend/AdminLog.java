package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = AdminLog.TABLE_NAME)
public class AdminLog {
    public static final String TABLE_NAME = "backend_adminlog";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "changing_id")
    private Integer changingId;

    @Column(name = "action", nullable = false, length = 255)
    private String action;

    @Column(name = "description", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "page_name", nullable = false, length = 255)
    private String pageName;

    @Column(name = "access_time", nullable = false)
    private LocalDateTime accessTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AdminUser user;
}
