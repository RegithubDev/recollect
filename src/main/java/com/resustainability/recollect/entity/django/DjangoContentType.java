package com.resustainability.recollect.entity.django;

import jakarta.persistence.*;

@Entity
@Table(
        name = DjangoContentType.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"app_label", "model"})
        }
)
public class DjangoContentType {
    public static final String TABLE_NAME = "django_content_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String appLabel;

    @Column(nullable = false, length = 100)
    private String model;

    public DjangoContentType() {
    }

    public DjangoContentType(Integer id, String appLabel, String model) {
        this.id = id;
        this.appLabel = appLabel;
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
