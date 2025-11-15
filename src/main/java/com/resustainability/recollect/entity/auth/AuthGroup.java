package com.resustainability.recollect.entity.auth;

import jakarta.persistence.*;

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
