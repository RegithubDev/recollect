package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderDistrictRequest;
import com.resustainability.recollect.dto.request.UpdateProviderDistrictRequest;
import com.resustainability.recollect.dto.response.IProviderDistrictResponse;
import com.resustainability.recollect.entity.backend.District;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ProviderDistrict;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.DistrictRepository;
import com.resustainability.recollect.repository.ProviderDistrictRepository;
import com.resustainability.recollect.repository.ProviderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderDistrictService {

    private final ProviderDistrictRepository pdRepo;
    private final ProviderRepository providerRepo;
    private final DistrictRepository districtRepo;

    public ProviderDistrictService(
            ProviderDistrictRepository pdRepo,
            ProviderRepository providerRepo,
            DistrictRepository districtRepo
    ) {
        this.pdRepo = pdRepo;
        this.providerRepo = providerRepo;
        this.districtRepo = districtRepo;
    }

    public Pager<IProviderDistrictResponse> list(Long providerId, Long districtId, SearchCriteria sc) {
        return Pager.of(
                pdRepo.list(providerId, districtId, sc.toPageRequest())
        );
    }


    public IProviderDistrictResponse getById(Long id) {
        ValidationUtils.validateId(id);

        IProviderDistrictResponse result = pdRepo.findDetails(id);
        if (result == null) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_DISTRICT );
        }
        return result;
    }


    @Transactional
    public Long add(AddProviderDistrictRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (pdRepo.existsByProvider_IdAndDistrict_Id(request.providerId(), request.districtId())) {
            throw new DataAlreadyExistException("This provider already mapped with this district.");
        }

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        District district = districtRepo.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        ProviderDistrict entity = new ProviderDistrict();
        entity.setProvider(provider);
        entity.setDistrict(district);
        entity.setScrapAllowed(request.scrapAllowed());
        entity.setBioAllowed(request.bioAllowed());
        entity.setIsActive(request.isActive());

        return pdRepo.save(entity).getId();
    }


    @Transactional
    public void update(UpdateProviderDistrictRequest request) {

        ValidationUtils.validateRequestBody(request);

        ProviderDistrict entity = pdRepo.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_DISTRICT));

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        District district = districtRepo.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        entity.setProvider(provider);
        entity.setDistrict(district);
        entity.setScrapAllowed(request.scrapAllowed());
        entity.setBioAllowed(request.bioAllowed());
        entity.setIsActive(request.isActive());

        pdRepo.save(entity);
    }


    @Transactional
    public void deleteById(Long id, boolean deactivate) {
        ValidationUtils.validateId(id);
        int updated = pdRepo.deleteProviderDistrict(id, !deactivate);
        if (updated == 0) throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_DISTRICT );
    }
}
