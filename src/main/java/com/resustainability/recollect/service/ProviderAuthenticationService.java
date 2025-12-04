package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.AddProviderAuthenticationRequest;
import com.resustainability.recollect.dto.request.UpdateProviderAuthenticationRequest;
import com.resustainability.recollect.dto.response.IProviderAuthenticationResponse;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ProviderAuthentication;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderAuthenticationRepository;
import com.resustainability.recollect.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderAuthenticationService {

    private final ProviderAuthenticationRepository authRepo;
    private final ProviderRepository providerRepo;

    public ProviderAuthenticationService(
            ProviderAuthenticationRepository authRepo,
            ProviderRepository providerRepo) {
        this.authRepo = authRepo;
        this.providerRepo = providerRepo;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddProviderAuthenticationRequest request) {
        ValidationUtils.validateRequestBody(request);

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        ProviderAuthentication entity = new ProviderAuthentication();
        entity.setOtp(request.otp());
        entity.setProvider(provider);

        return authRepo.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateProviderAuthenticationRequest request) {
        ValidationUtils.validateRequestBody(request);

        ProviderAuthentication entity = authRepo.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        if (request.otp() != null) {
            entity.setOtp(request.otp());
        }

        authRepo.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IProviderAuthenticationResponse getById(Long id) {
        ValidationUtils.validateId(id);
        IProviderAuthenticationResponse data = authRepo.findDetails(id);

        if (data == null) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER);
        }

        return data;
    }
}
