package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateProviderLocalBodyRequest;
import com.resustainability.recollect.dto.response.IProviderLocalBodyResponse;
import com.resustainability.recollect.entity.backend.LocalBody;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.entity.backend.ProviderLocalBody;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.LocalBodyRepository;
import com.resustainability.recollect.repository.ProviderLocalBodyRepository;
import com.resustainability.recollect.repository.ProviderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderLocalBodyService {

    private final ProviderLocalBodyRepository plbRepo;
    private final ProviderRepository providerRepo;
    private final LocalBodyRepository localBodyRepo;

    public ProviderLocalBodyService(
            ProviderLocalBodyRepository plbRepo,
            ProviderRepository providerRepo,
            LocalBodyRepository localBodyRepo
    ) {
        this.plbRepo = plbRepo;
        this.providerRepo = providerRepo;
        this.localBodyRepo = localBodyRepo;
    }

    public Pager<IProviderLocalBodyResponse> list(Long providerId, Long localBodyId, SearchCriteria sc) {
        return Pager.of(
                plbRepo.list(providerId, localBodyId, sc.toPageRequest())
        );
    }

    public IProviderLocalBodyResponse getById(Long id) {
        ValidationUtils.validateId(id);

        IProviderLocalBodyResponse result = plbRepo.findDetails(id);
        if (result == null) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_LOCALBODY);
        }
        return result;
    }

    @Transactional
    public Long add(AddProviderLocalBodyRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (plbRepo.existsByProvider_IdAndLocalBody_Id(request.providerId(), request.localBodyId())) {
            throw new DataAlreadyExistException("This provider already mapped with this localbody.");
        }

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        LocalBody localBody = localBodyRepo.findById(request.localBodyId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCALBODY));

        ProviderLocalBody entity = new ProviderLocalBody();
        entity.setProvider(provider);
        entity.setLocalBody(localBody);
        entity.setIsActive(request.isActive());

        return plbRepo.save(entity).getId();
    }

    @Transactional
    public void update(UpdateProviderLocalBodyRequest request) {

        ValidationUtils.validateRequestBody(request);

        ProviderLocalBody entity = plbRepo.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_LOCALBODY));

        Provider provider = providerRepo.findById(request.providerId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER));

        LocalBody localBody = localBodyRepo.findById(request.localBodyId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCALBODY));

        entity.setProvider(provider);
        entity.setLocalBody(localBody);
        entity.setIsActive(request.isActive());

        plbRepo.save(entity);
    }

    @Transactional
    public void deleteById(Long id, boolean deactivate) {
        ValidationUtils.validateId(id);
        int updated = plbRepo.deleteProviderLocalBody(id, !deactivate);
        if (updated == 0) throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER_LOCALBODY);
    }
}
