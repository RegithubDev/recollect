package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.repository.ProviderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(
            ProviderRepository providerRepository
    ) {
        this.providerRepository = providerRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Optional<Provider> findByUsernameAndPassword(String username, String password) {
        ValidationUtils.validateUsername(username);
        ValidationUtils.validatePassword(password);
        return providerRepository.findByUsernameAndPassword(username, password);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int refreshLastLoginAtById(Long providerId) {
        ValidationUtils.validateUserId(providerId);
        return providerRepository.updateLastLoginAtById(providerId, LocalDateTime.now());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int refreshTokenAtById(Long providerId) {
        ValidationUtils.validateUserId(providerId);
        return providerRepository.updateTokenAtById(providerId, LocalDateTime.now());
    }
}
