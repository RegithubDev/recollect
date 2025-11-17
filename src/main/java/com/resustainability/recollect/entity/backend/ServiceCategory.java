package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = ServiceCategory.TABLE_NAME)
public class ServiceCategory {
    public static final String TABLE_NAME = "backend_servicecategory";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "service_title", nullable = false, columnDefinition = "LONGTEXT")
    private String serviceTitle;

    @Column(name = "service_subtitle", columnDefinition = "LONGTEXT")
    private String serviceSubtitle;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled;

    public ServiceCategory() {
    }

    public ServiceCategory(Long id, String serviceName, String serviceTitle, String serviceSubtitle, String icon, Boolean isActive, Boolean isDisabled) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceTitle = serviceTitle;
        this.serviceSubtitle = serviceSubtitle;
        this.icon = icon;
        this.isActive = isActive;
        this.isDisabled = isDisabled;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ServiceCategory entity = (ServiceCategory) object;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceSubtitle() {
        return serviceSubtitle;
    }

    public void setServiceSubtitle(String serviceSubtitle) {
        this.serviceSubtitle = serviceSubtitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }
}
