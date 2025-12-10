package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = AakriContactNumber.TABLE_NAME)
public class AakriContactNumber {
    public static final String TABLE_NAME = "Backend_aakricontactnumber";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "is_kerala", nullable = false)
    private Boolean isKerala;

    public AakriContactNumber() {
    }

    public AakriContactNumber(Long id, String phoneNumber, Boolean isKerala) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.isKerala = isKerala;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AakriContactNumber entity = (AakriContactNumber) object;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getKerala() {
        return isKerala;
    }

    public void setKerala(Boolean kerala) {
        isKerala = kerala;
    }
}
