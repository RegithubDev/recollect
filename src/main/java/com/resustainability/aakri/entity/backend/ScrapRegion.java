package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ScrapRegion.TABLE_NAME)
public class ScrapRegion {
    public static final String TABLE_NAME = "backend_scrapregion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_name", length = 100)
    private String regionName;

    @Column(name = "border_polygon", columnDefinition = "LONGTEXT")
    private String borderPolygon;

    @Column(name = "region_weekday_current", columnDefinition = "json", nullable = false)
    private String regionWeekdayCurrent;

    @Column(name = "region_weekday_next", columnDefinition = "json", nullable = false)
    private String regionWeekdayNext;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
}
