package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = OffersAndCoupons.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"coupon_id"})
})
public class OffersAndCoupons {
    public static final String TABLE_NAME = "Backend_offersandcoupons";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id", nullable = false, length = 20)
    private String couponId;

    @Column(name = "title", nullable = false, columnDefinition = "LONGTEXT")
    private String title;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "discount", length = 1000)
    private String discount;

    @Column(name = "coupon_code", length = 1000)
    private String couponCode;

    @Column(name = "plain_link", length = 1000)
    private String plainLink;

    @Column(name = "affiliate_link", length = 1000)
    private String affiliateLink;

    @Column(name = "merchant_id", nullable = false, length = 50)
    private String merchantId;

    @Column(name = "merchant_name", nullable = false, length = 100)
    private String merchantName;

    @Column(name = "merchant_logo", length = 1000)
    private String merchantLogo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
