package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = State.TABLE_NAME)
public class State {
    public static final String TABLE_NAME = "Backend_state";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_image", length = 100)
    private String stateImage;

    @Column(name = "state_name", length = 100)
    private String stateName;

    @Column(name = "state_code", length = 100)
    private String stateCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public State() {
    }

    public State(Long id, String stateImage, String stateName, String stateCode, Boolean isActive, Boolean isDeleted, Country country) {
        this.id = id;
        this.stateImage = stateImage;
        this.stateName = stateName;
        this.stateCode = stateCode;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.country = country;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        State entity = (State) object;
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

    public String getStateImage() {
        return stateImage;
    }

    public void setStateImage(String stateImage) {
        this.stateImage = stateImage;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
