package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = EmployeeInfo.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = "employee_id"),
        @UniqueConstraint(columnNames = "aadhar_number")
})
public class EmployeeInfo {
    public static final String TABLE_NAME = "Backend_employeeinfo";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false, length = 20)
    private String employeeId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email", length = 254)
    private String email;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "photo", length = 100)
    private String photo;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "aadhar_number", nullable = false, length = 12)
    private String aadharNumber;

    @Column(name = "address", columnDefinition = "LONGTEXT")
    private String address;

    @Column(name = "designation", nullable = false, length = 100)
    private String designation;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name = "date_of_leaving")
    private LocalDate dateOfLeaving;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @Column(name = "ifsc_code", length = 20)
    private String ifscCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;
}
