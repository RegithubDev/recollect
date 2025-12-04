package com.resustainability.recollect.repository;

import com.resustainability.recollect.dto.response.IProviderAuthenticationResponse;
import com.resustainability.recollect.entity.backend.ProviderAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderAuthenticationRepository extends JpaRepository<ProviderAuthentication, Long> {

    @Query("""
        SELECT pa.id AS id,
               pa.otp AS otp,
               pa.provider.id AS providerId
        FROM ProviderAuthentication pa
        WHERE pa.id = :id
    """)
    IProviderAuthenticationResponse findDetails(Long id);
}
