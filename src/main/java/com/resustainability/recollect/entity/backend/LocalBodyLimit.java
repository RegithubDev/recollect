package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
    name = LocalBodyLimit.TABLE_NAME,
    uniqueConstraints = @UniqueConstraint(columnNames = {"available_date","localbody_id"})
)
public class LocalBodyLimit {
    public static final String TABLE_NAME = "backend_localbodylimit";

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
    @JoinColumn(name = "localbody_id", nullable = false)
    private LocalBody localbody;

    public LocalBodyLimit() {
    }

    public LocalBodyLimit(Long id, LocalDate availableDate, Integer limit, Integer remainingSlots, LocalBody localbody) {
        this.id = id;
        this.availableDate = availableDate;
        this.limit = limit;
        this.remainingSlots = remainingSlots;
        this.localbody = localbody;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocalBodyLimit that = (LocalBodyLimit) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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

    public LocalBody getLocalbody() {
        return localbody;
    }

    public void setLocalbody(LocalBody localbody) {
        this.localbody = localbody;
    }
}
