package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderScrapRegionRequest;
import com.resustainability.recollect.dto.request.UpdateProviderScrapRegionRequest;
import com.resustainability.recollect.dto.response.IProviderScrapRegionResponse;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ScrapRegion;
import com.resustainability.recollect.entity.backend.ProviderScrapRegion;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderScrapRegionService {

    private final ProviderScrapRegionRepository repo;
    private final ProviderRepository providerRepo;
    private final ScrapRegionRepository scrapRegionRepo;

    public ProviderScrapRegionService(
            ProviderScrapRegionRepository repo,
            ProviderRepository providerRepo,
            ScrapRegionRepository scrapRegionRepo
    ) {
        this.repo = repo;
        this.providerRepo = providerRepo;
        this.scrapRegionRepo = scrapRegionRepo;
    }

    public Pager<IProviderScrapRegionResponse> list(Long providerId, Long scrapRegionId, SearchCriteria sc) {
        return Pager.of(repo.list(providerId, scrapRegionId, sc.toPageRequest()));
    }

    public IProviderScrapRegionResponse getById(Long id) {
        ValidationUtils.validateId(id);
        IProviderScrapRegionResponse result = repo.findDetails(id);
        if (result == null)
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_SCRAP_REGION);
        return result;
    }

    @Transactional
    public Long add(AddProviderScrapRegionRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (repo.existsByProvider_IdAndScrapRegion_Id(request.providerId(), request.scrapRegionId())) {
            throw new DataAlreadyExistException("Provider is already mapped to this Scrap Region.");
        }

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        ScrapRegion scrapRegion = scrapRegionRepo.findById(request.scrapRegionId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));

        ProviderScrapRegion entity = new ProviderScrapRegion();
        entity.setProvider(provider);
        entity.setScrapRegion(scrapRegion);
        entity.setIsActive(request.isActive());

        return repo.save(entity).getId();
    }

    @Transactional
    public void update(UpdateProviderScrapRegionRequest request) {
        ValidationUtils.validateRequestBody(request);

        ProviderScrapRegion entity = repo.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_SCRAP_REGION));

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        ScrapRegion scrapRegion = scrapRegionRepo.findById(request.scrapRegionId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_SCRAP_REGION));

        entity.setProvider(provider);
        entity.setScrapRegion(scrapRegion);
        entity.setIsActive(request.isActive());

        repo.save(entity);
    }

    @Transactional
    public void deleteById(Long id, boolean deactivate) {
        ValidationUtils.validateId(id);
        int updated = repo.deleteProviderScrapRegion(id, !deactivate);
        if (updated == 0)
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_SCRAP_REGION);
    }
}
