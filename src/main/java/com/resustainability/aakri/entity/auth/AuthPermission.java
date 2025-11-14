package com.resustainability.aakri.entity.auth;

import com.resustainability.aakri.entity.django.DjangoContentType;
import jakarta.persistence.*;

@Entity
@Table(
        name = AuthPermission.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"content_type_id", "codename"})
        }
)
public class AuthPermission {
    public static final String TABLE_NAME = "auth_permission";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "content_type_id", nullable = false)
    private DjangoContentType contentType;

    @Column(nullable = false, length = 100)
    private String codename;

    public AuthPermission() {}

    public AuthPermission(Integer id, String name, DjangoContentType contentType, String codename) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.codename = codename;
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

    public DjangoContentType getContentType() {
        return contentType;
    }

    public void setContentType(DjangoContentType contentType) {
        this.contentType = contentType;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }
}
