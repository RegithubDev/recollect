package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderPickupWeightLimitRequest;
import com.resustainability.recollect.dto.request.UpdateProviderPickupWeightLimitRequest;
import com.resustainability.recollect.dto.response.IProviderPickupWeightLimitResponse;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ProviderPickupWeightLimit;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderPickupWeightLimitRepository;
import com.resustainability.recollect.repository.ProviderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

@Service
public class ProviderPickupWeightLimitService {

    private final ProviderPickupWeightLimitRepository repository;
    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderPickupWeightLimitService(
            ProviderPickupWeightLimitRepository repository,
            ProviderRepository providerRepository
    ) {
        this.repository = repository;
        this.providerRepository = providerRepository;
    }

    public Pager<IProviderPickupWeightLimitResponse> list(
            Long providerId,
            SearchCriteria searchCriteria
    ) {
        return Pager.of(
                repository.findAllPaged(providerId, searchCriteria.toPageRequest())
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IProviderPickupWeightLimitResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByIdDetails(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUPWEIGHT_LIMIT)
                );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddProviderPickupWeightLimitRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (repository.existsByProvider_Id(request.providerId())) {
            throw new DataAlreadyExistException(
                    "Pickup weight limit already exists for this provider"
            );
        }

        Provider provider = providerRepository.findById(request.providerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER)
                );

        ProviderPickupWeightLimit entity = new ProviderPickupWeightLimit();
        entity.setWeightLimit(request.weightLimit());
        entity.setProvider(provider);

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateProviderPickupWeightLimitRequest request) {

        ValidationUtils.validateRequestBody(request);

        ProviderPickupWeightLimit entity = repository.findById(request.id())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUPWEIGHT_LIMIT)
                );

        entity.setWeightLimit(request.weightLimit());

        repository.save(entity);
    }
}
