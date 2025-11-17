package com.resustainability.recollect.entity.auth;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = AuthGroup.TABLE_NAME)
public class AuthGroup {
    public static final String TABLE_NAME = "auth_group";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    public AuthGroup() {}

    public AuthGroup(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AuthGroup entity = (AuthGroup) object;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
