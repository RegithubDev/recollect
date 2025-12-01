package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.AddProviderAddOrderLimitRequest;
import com.resustainability.recollect.dto.request.UpdateProviderAddOrderLimitRequest;
import com.resustainability.recollect.dto.response.IProviderAddOrderLimitResponse;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ProviderAddOrderLimit;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.InvalidDataException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderAddOrderLimitRepository;
import com.resustainability.recollect.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderAddOrderLimitService {

    private final ProviderAddOrderLimitRepository limitRepository;
    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderAddOrderLimitService(
            ProviderAddOrderLimitRepository limitRepository,
            ProviderRepository providerRepository
    ) {
        this.limitRepository = limitRepository;
        this.providerRepository = providerRepository;
    }



    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddProviderAddOrderLimitRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (request.currentLimit() > request.maxLimit()) {
            throw new InvalidDataException(Default.ERROR_LIMIT_EXCEEDED);
        }

        if (limitRepository.existsByProviderId(request.providerId())) {
            throw new DataAlreadyExistException(
                    "Order limit already exists for provider (" + request.providerId() + ")"
            );
        }

        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        ProviderAddOrderLimit entity = new ProviderAddOrderLimit();
        entity.setMaxLimit(request.maxLimit());
        entity.setCurrentLimit(request.currentLimit());
        entity.setProvider(provider);

        ProviderAddOrderLimit saved = limitRepository.save(entity);

        return saved.getId();
    }



    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateProviderAddOrderLimitRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (request.currentLimit() > request.maxLimit()) {
            throw new InvalidDataException(Default.ERROR_LIMIT_EXCEEDED);
        }

        ProviderAddOrderLimit entity = limitRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_ORDER_LIMIT));

        
        if (limitRepository.existsByProviderIdExceptId(request.providerId(), request.id())) {
            throw new DataAlreadyExistException(
                    "Order limit already exists for provider (" + request.providerId() + ")"
            );
        }

        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        entity.setMaxLimit(request.maxLimit());
        entity.setCurrentLimit(request.currentLimit());
        entity.setProvider(provider);

        limitRepository.save(entity);
    }



    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IProviderAddOrderLimitResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return limitRepository.findDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_ORDER_LIMIT));
    }



    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Page<IProviderAddOrderLimitResponse> list(String search, Long providerId, Pageable pageable) {
        return limitRepository.findAllPaged(
                (search == null ? "" : search.trim()),
                providerId,
                pageable
        );
    }
}
