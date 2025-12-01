package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderWardRequest;
import com.resustainability.recollect.dto.request.UpdateProviderWardRequest;
import com.resustainability.recollect.dto.response.IProviderWardResponse;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ProviderWard;
import com.resustainability.recollect.entity.backend.Ward;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.ProviderRepository;
import com.resustainability.recollect.repository.ProviderWardRepository;
import com.resustainability.recollect.repository.WardRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderWardService {

    private final ProviderWardRepository pwRepo;
    private final ProviderRepository providerRepo;
    private final WardRepository wardRepo;

    public ProviderWardService(
            ProviderWardRepository pwRepo,
            ProviderRepository providerRepo,
            WardRepository wardRepo
    ) {
        this.pwRepo = pwRepo;
        this.providerRepo = providerRepo;
        this.wardRepo = wardRepo;
    }

    public Pager<IProviderWardResponse> list(Long providerId, Long wardId, SearchCriteria sc) {
        return Pager.of(
                pwRepo.list(providerId, wardId, sc.toPageRequest())
        );
    }

    public IProviderWardResponse getById(Long id) {
        ValidationUtils.validateId(id);
        IProviderWardResponse result = pwRepo.findDetails(id);

        if (result == null) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_WARD);
        }
        return result;
    }

    @Transactional
    public Long add(AddProviderWardRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (pwRepo.existsByProvider_IdAndWard_Id(request.providerId(), request.wardId())) {
            throw new DataAlreadyExistException("This provider already mapped with this ward.");
        }

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        Ward ward = wardRepo.findById(request.wardId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));

        ProviderWard entity = new ProviderWard();
        entity.setProvider(provider);
        entity.setWard(ward);
        entity.setIsActive(request.isActive());

        return pwRepo.save(entity).getId();
    }

    @Transactional
    public void update(UpdateProviderWardRequest request) {

        ValidationUtils.validateRequestBody(request);

        ProviderWard entity = pwRepo.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_WARD));

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        Ward ward = wardRepo.findById(request.wardId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));

        entity.setProvider(provider);
        entity.setWard(ward);
        entity.setIsActive(request.isActive());

        pwRepo.save(entity);
    }

    @Transactional
    public void deleteById(Long id, boolean deactivate) {
        ValidationUtils.validateId(id);
        int updated = pwRepo.deleteProviderWard(id, !deactivate);
        if (updated == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_WARD);
        }
    }
}
