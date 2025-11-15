package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = Ward.TABLE_NAME)
public class Ward {
    public static final String TABLE_NAME = "backend_ward";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ward_no", nullable = false)
    private Integer wardNo;

    @Column(name = "ward_name", length = 100)
    private String wardName;

    @Column(name = "ward_weekday_current", columnDefinition = "json", nullable = false)
    private String wardWeekdayCurrent;

    @Column(name = "ward_weekday_next", columnDefinition = "json", nullable = false)
    private String wardWeekdayNext;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "localbody_id", nullable = false)
    private LocalBody localbody;
}
