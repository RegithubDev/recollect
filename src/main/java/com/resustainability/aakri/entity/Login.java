package com.resustainability.aakri.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "test")
public class Login {

    @Id
    @Column(insertable = false, updatable = false)
    private Long id;  

    private String name;

    public Login() {}

    public Login(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Login [name=" + name + "]";
    }
}
