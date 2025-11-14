package com.resustainability.aakri.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = CustomerPhoneNumberChange.TABLE_NAME)
public class CustomerPhoneNumberChange {
    public static final String TABLE_NAME = "backend_customerphonenumberchange";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "otp", nullable = false, length = 10)
    private String otp;
}
