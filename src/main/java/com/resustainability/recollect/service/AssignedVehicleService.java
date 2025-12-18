package com.resustainability.recollect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddAssignedVehicleRequest;
import com.resustainability.recollect.dto.request.UpdateAssignedVehicleRequest;
import com.resustainability.recollect.dto.response.IAssignedVehicleResponse;
import com.resustainability.recollect.entity.backend.AssignedVehicle;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.AssignedVehicleRepository;
import com.resustainability.recollect.repository.PickupVehicleRepository;
import com.resustainability.recollect.repository.ProviderRepository;

@Service
public class AssignedVehicleService {

    private final AssignedVehicleRepository repository;
    private final PickupVehicleRepository vehicleRepository;
    private final ProviderRepository providerRepository;

    @Autowired
    public AssignedVehicleService(
            AssignedVehicleRepository repository,
            PickupVehicleRepository vehicleRepository,
            ProviderRepository providerRepository
    ) {
        this.repository = repository;
        this.vehicleRepository = vehicleRepository;
        this.providerRepository = providerRepository;
    }

    public Pager<IAssignedVehicleResponse> list(SearchCriteria searchCriteria) {
        return Pager.of(
                repository.findAllPaged(searchCriteria.toPageRequest())
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IAssignedVehicleResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByAssignedVehicleId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ASSIGNED_VEHILE));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddAssignedVehicleRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (repository.existsByVehicleIdAndProviderId(
                request.vehicleId(), request.providerId())) {
            throw new DataAlreadyExistException("Vehicle already assigned to provider");
        }

        AssignedVehicle entity = new AssignedVehicle();
        entity.setVehicle(
                vehicleRepository.findById(request.vehicleId())
                        .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE))
        );
        entity.setProvider(
                providerRepository.findById(request.providerId())
                        .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER))
        );

        return repository.save(entity).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateAssignedVehicleRequest request) {
        ValidationUtils.validateRequestBody(request);

        AssignedVehicle entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ASSIGNED_VEHILE));

        entity.setVehicle(
                vehicleRepository.findById(request.vehicleId())
                        .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE))
        );
        entity.setProvider(
                providerRepository.findById(request.providerId())
                        .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PROVIDER))
        );

        repository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        ValidationUtils.validateId(id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_ASSIGNED_VEHILE);
        }

        repository.deleteById(id);
    }
}
