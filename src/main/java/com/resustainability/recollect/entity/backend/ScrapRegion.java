package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import org.locationtech.jts.geom.MultiPolygon;

import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Column(name = "geometry", columnDefinition = "MULTIPOLYGON SRID 4326")
    private MultiPolygon geometry;

    public ScrapRegion() {
    }

    public ScrapRegion(Long id, String regionName, String borderPolygon, String regionWeekdayCurrent, String regionWeekdayNext, Boolean isActive, Boolean isDeleted, District district, MultiPolygon geometry) {
        this.id = id;
        this.regionName = regionName;
        this.borderPolygon = borderPolygon;
        this.regionWeekdayCurrent = regionWeekdayCurrent;
        this.regionWeekdayNext = regionWeekdayNext;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.district = district;
        this.geometry = geometry;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ScrapRegion entity = (ScrapRegion) object;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBorderPolygon() {
        return borderPolygon;
    }

    public void setBorderPolygon(String borderPolygon) {
        this.borderPolygon = borderPolygon;
    }

    public String getRegionWeekdayCurrent() {
        return regionWeekdayCurrent;
    }

    public void setRegionWeekdayCurrent(String regionWeekdayCurrent) {
        this.regionWeekdayCurrent = regionWeekdayCurrent;
    }

    public String getRegionWeekdayNext() {
        return regionWeekdayNext;
    }

    public void setRegionWeekdayNext(String regionWeekdayNext) {
        this.regionWeekdayNext = regionWeekdayNext;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public MultiPolygon getGeometry() {
        return geometry;
    }

    public void setGeometry(MultiPolygon geometry) {
        this.geometry = geometry;
    }
}
