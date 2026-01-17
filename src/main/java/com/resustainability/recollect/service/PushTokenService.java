package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.RegisterFcmTokenRequest;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.backend.UserFcmToken;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.AdminUserRepository;
import com.resustainability.recollect.repository.CustomerRepository;
import com.resustainability.recollect.repository.ProviderRepository;
import com.resustainability.recollect.repository.UserFcmTokenRepository;
import com.resustainability.recollect.tag.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PushTokenService {
    private final UserFcmTokenRepository userFcmTokenRepository;
    private final CustomerRepository customerRepository;
    private final ProviderRepository providerRepository;
    private final AdminUserRepository adminUserRepository;
    private final SecurityService securityService;

    @Autowired
    public PushTokenService(
            UserFcmTokenRepository userFcmTokenRepository,
            CustomerRepository customerRepository,
            ProviderRepository providerRepository,
            AdminUserRepository adminUserRepository,
            SecurityService securityService
    ) {
        this.userFcmTokenRepository = userFcmTokenRepository;
        this.customerRepository = customerRepository;
        this.providerRepository = providerRepository;
        this.adminUserRepository = adminUserRepository;
        this.securityService = securityService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Optional<UserFcmToken> getUserFcmTokenDetails(String fcmToken) {
        return userFcmTokenRepository
                .findByFcmToken(fcmToken)
                .filter(model -> Boolean.TRUE.equals(model.getActive()));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<String> getAllActiveTokensByCustomerId(Long customerId) {
        return userFcmTokenRepository.findActiveTokensByCustomerId(customerId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<String> getAllActiveTokensByProviderId(Long providerId) {
        return userFcmTokenRepository.findActiveTokensByProviderId(providerId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void registerToken(RegisterFcmTokenRequest request) {
        ValidationUtils.validateRequestBody(request);

        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final UserFcmToken entity = userFcmTokenRepository
                .findByFcmToken(request.fcmToken())
                .map(model -> {
                    model.setActive(true);
                    return model;
                })
                .orElseGet(() -> new UserFcmToken(
                        null,
                        request.fcmToken(),
                        request.deviceId(),
                        request.platform(),
                        null,
                        null,
                        null,
                        null,
                        LocalDateTime.now(),
                        true
                ));

        if (Boolean.TRUE.equals(user.getIsCustomer())) {
            entity.setCustomer(
                    customerRepository.getReferenceById(user.getId())
            );
        } else if (Boolean.TRUE.equals(user.getIsProvider())) {
            entity.setProvider(
                    providerRepository.getReferenceById(user.getId())
            );
        } else if (Boolean.TRUE.equals(user.getIsAdmin())) {
            entity.setAdmin(
                    adminUserRepository.getReferenceById(user.getId())
            );
        } else {
            throw new InvalidDataException("Supported for " + String.join(", ", Role.list()));
        }

        try {
            userFcmTokenRepository.save(entity);
        } catch (DataIntegrityViolationException ignored) {}
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deactivateTokens(Set<String> tokens) {
        if (CollectionUtils.isNonBlank(tokens)) {
            userFcmTokenRepository.updateByFcmTokensInBatch(tokens, false);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteTokens(Set<String> tokens) {
        if (CollectionUtils.isNonBlank(tokens)) {
            userFcmTokenRepository.deleteByFcmTokensInBatch(tokens);
        }
    }
}
