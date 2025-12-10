package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = AdminUserDistrict.TABLE_NAME)
public class AdminUserDistrict {
    public static final String TABLE_NAME = "Backend_adminuserdistrict";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", nullable = false)
    private AdminUser adminUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
}
