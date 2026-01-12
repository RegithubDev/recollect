package com.resustainability.recollect.repository;

import com.resustainability.recollect.entity.backend.UserFcmToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.Optional;

public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, Long> {
    Optional<UserFcmToken> findByFcmToken(String fcmToken);

    @Query(value = """
        SELECT u.fcmToken
        FROM UserFcmToken u
        WHERE u.customer.id = :customerId
          AND u.isActive = true
    """)
    List<String> findActiveTokensByCustomerId(
            @Param("customerId") Long customerId
    );

    @Query(value = """
        SELECT u.fcmToken
        FROM UserFcmToken u
        WHERE u.provider.id = :providerId
          AND u.isActive = true
    """)
    List<String> findActiveTokensByProviderId(
            @Param("providerId") Long providerId
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        UPDATE UserFcmToken u
            SET u.isActive = :isActive
        WHERE u.fcmToken IN :tokens
    """)
    void updateByFcmTokensInBatch(@Param("tokens") Set<String> tokens, @Param("isActive") boolean isActive);

    @Modifying
    @Query(value = """
        DELETE FROM UserFcmToken u
        WHERE u.fcmToken IN :tokens
    """)
    void deleteByFcmTokensInBatch(@Param("tokens") Set<String> tokens);
}
