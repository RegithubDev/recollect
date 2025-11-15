package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = Vendors.TABLE_NAME)
public class Vendors {
    public static final String TABLE_NAME = "backend_vendors";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "pin_code", nullable = false, length = 6)
    private String pinCode;

    @Column(name = "address", nullable = false, columnDefinition = "LONGTEXT")
    private String address;

    @Column(name = "landmark", nullable = false, length = 100)
    private String landmark;

    @Column(name = "code", nullable = false, length = 20, unique = true)
    private String code;

    @Column(name = "materials", columnDefinition = "json", nullable = false)
    private String materials;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "GST_number", length = 15)
    private String gstNumber;

    @Column(name = "GST_address", columnDefinition = "LONGTEXT")
    private String gstAddress;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
