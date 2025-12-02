package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderServiceLocationFlagRequest;
import com.resustainability.recollect.dto.request.UpdateProviderServiceLocationFlagRequest;
import com.resustainability.recollect.dto.response.IProviderServiceLocationFlagResponse;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ProviderServiceLocationFlag;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderServiceLocationFlagService {

    private final ProviderServiceLocationFlagRepository repo;
    private final ProviderRepository providerRepo;

    public ProviderServiceLocationFlagService(
        ProviderServiceLocationFlagRepository repo,
        ProviderRepository providerRepo
    ) {
        this.repo = repo;
        this.providerRepo = providerRepo;
    }

    public Pager<IProviderServiceLocationFlagResponse> list(Long providerId, SearchCriteria sc) {
        return Pager.of(repo.list(providerId, sc.toPageRequest()));
    }

    public IProviderServiceLocationFlagResponse getById(Long id) {
        ValidationUtils.validateId(id);
        IProviderServiceLocationFlagResponse result = repo.findDetails(id);
        if (result == null)
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_LOCATION_FLAGS);
        return result;
    }

    @Transactional
    public Long add(AddProviderServiceLocationFlagRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (repo.existsByProvider_Id(request.providerId())) {
            throw new DataAlreadyExistException("Flags for this provider already exist.");
        }

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        ProviderServiceLocationFlag entity = new ProviderServiceLocationFlag();
        entity.setProvider(provider);
        entity.setAllScrapDistrict(request.allScrapDistrict());
        entity.setAllBioDistrict(request.allBioDistrict());
        entity.setAllScrapRegions(request.allScrapRegions());
        entity.setAllLocalBodies(request.allLocalBodies());
        entity.setAllWards(request.allWards());

        return repo.save(entity).getId();
    }

    @Transactional
    public void update(UpdateProviderServiceLocationFlagRequest request) {
        ValidationUtils.validateRequestBody(request);

        ProviderServiceLocationFlag entity = repo.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_LOCATION_FLAGS));

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        entity.setProvider(provider);
        entity.setAllScrapDistrict(request.allScrapDistrict());
        entity.setAllBioDistrict(request.allBioDistrict());
        entity.setAllScrapRegions(request.allScrapRegions());
        entity.setAllLocalBodies(request.allLocalBodies());
        entity.setAllWards(request.allWards());

        repo.save(entity);
    }
}
