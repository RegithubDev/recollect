package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminUserDistrict.TABLE_NAME)
public class AdminUserDistrict {
    public static final String TABLE_NAME = "backend_adminuserdistrict";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_user_id", nullable = false)
    private AdminUser adminUser;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
}
