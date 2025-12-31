package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = ScrapRegionAvailability.TABLE_NAME)
public class ScrapRegionAvailability {
    public static final String TABLE_NAME = "backend_scrapregionavailability";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    @Column(name = "`limit`", nullable = false)
    private Integer limit;

    @Column(name = "remaining_slots", nullable = false)
    private Integer remainingSlots;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_region_id", nullable = false)
    private ScrapRegion scrapRegion;

    public ScrapRegionAvailability() {
    }

    public ScrapRegionAvailability(Long id, LocalDate availableDate, Integer limit, Integer remainingSlots, ScrapRegion scrapRegion) {
        this.id = id;
        this.availableDate = availableDate;
        this.limit = limit;
        this.remainingSlots = remainingSlots;
        this.scrapRegion = scrapRegion;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ScrapRegionAvailability entity = (ScrapRegionAvailability) object;
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

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getRemainingSlots() {
        return remainingSlots;
    }

    public void setRemainingSlots(Integer remainingSlots) {
        this.remainingSlots = remainingSlots;
    }

    public ScrapRegion getScrapRegion() {
        return scrapRegion;
    }

    public void setScrapRegion(ScrapRegion scrapRegion) {
        this.scrapRegion = scrapRegion;
    }
}
