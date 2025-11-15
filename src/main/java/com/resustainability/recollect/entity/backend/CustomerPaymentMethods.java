package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = CustomerPaymentMethods.TABLE_NAME)
public class CustomerPaymentMethods {
    public static final String TABLE_NAME = "backend_customerpaymentmethods";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beneficiary", length = 100)
    private String beneficiary;

    @Column(name = "account_number", length = 100)
    private String accountNumber;

    @Column(name = "ifsc", length = 100)
    private String ifsc;

    @Column(name = "upi", length = 100)
    private String upi;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "transaction_mode", length = 100)
    private String transactionMode;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private BwgClient client;
}
