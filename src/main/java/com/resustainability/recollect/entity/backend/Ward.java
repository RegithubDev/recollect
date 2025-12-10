package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = Ward.TABLE_NAME)
public class Ward {
    public static final String TABLE_NAME = "Backend_ward";

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localbody_id", nullable = false)
    private LocalBody localbody;

    public Ward() {
    }

    public Ward(Long id, Integer wardNo, String wardName, String wardWeekdayCurrent, String wardWeekdayNext, Boolean isActive, Boolean isDeleted, LocalBody localbody) {
        this.id = id;
        this.wardNo = wardNo;
        this.wardName = wardName;
        this.wardWeekdayCurrent = wardWeekdayCurrent;
        this.wardWeekdayNext = wardWeekdayNext;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.localbody = localbody;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Ward entity = (Ward) object;
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

    public Integer getWardNo() {
        return wardNo;
    }

    public void setWardNo(Integer wardNo) {
        this.wardNo = wardNo;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardWeekdayCurrent() {
        return wardWeekdayCurrent;
    }

    public void setWardWeekdayCurrent(String wardWeekdayCurrent) {
        this.wardWeekdayCurrent = wardWeekdayCurrent;
    }

    public String getWardWeekdayNext() {
        return wardWeekdayNext;
    }

    public void setWardWeekdayNext(String wardWeekdayNext) {
        this.wardWeekdayNext = wardWeekdayNext;
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

    public LocalBody getLocalbody() {
        return localbody;
    }

    public void setLocalbody(LocalBody localbody) {
        this.localbody = localbody;
    }
}
