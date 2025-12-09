package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddPickupVehicleDistrictRequest;
import com.resustainability.recollect.dto.request.UpdatePickupVehicleDistrictRequest;
import com.resustainability.recollect.dto.response.IPickupVehicleDistrictResponse;
import com.resustainability.recollect.entity.backend.District;
import com.resustainability.recollect.entity.backend.PickupVehicle;
import com.resustainability.recollect.entity.backend.PickupVehicleDistrict;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.DistrictRepository;
import com.resustainability.recollect.repository.PickupVehicleDistrictRepository;
import com.resustainability.recollect.repository.PickupVehicleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

@Service
public class PickupVehicleDistrictService {

    private final PickupVehicleDistrictRepository repository;
    private final DistrictRepository districtRepository;
    private final PickupVehicleRepository vehicleRepository;

    public PickupVehicleDistrictService(
            PickupVehicleDistrictRepository repository,
            DistrictRepository districtRepository,
            PickupVehicleRepository vehicleRepository
    ) {
        this.repository = repository;
        this.districtRepository = districtRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Pager<IPickupVehicleDistrictResponse> list(
            Long districtId,
            Long vehicleId,
            SearchCriteria searchCriteria
    ) {
        return Pager.of(
                repository.findAllPaged(
                        districtId,
                        vehicleId,
                        searchCriteria.getQ(),
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IPickupVehicleDistrictResponse getById(Long id) {
        ValidationUtils.validateId(id);
        return repository.findByPickupVehicleDistrictId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUPVEHICLEDISTRICT));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddPickupVehicleDistrictRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (repository.existsByDistrictAndVehicle(request.districtId(), request.vehicleId())) {
            throw new DataAlreadyExistException("This vehicle is already assigned to this district");
        }

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        PickupVehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE));

        PickupVehicleDistrict entity = new PickupVehicleDistrict();
        entity.setDistrict(district);
        entity.setVehicle(vehicle);
        entity.setIsActive(true);

        PickupVehicleDistrict saved = repository.save(entity);
        return saved.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdatePickupVehicleDistrictRequest request) {
        ValidationUtils.validateRequestBody(request);

        PickupVehicleDistrict entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUPVEHICLEDISTRICT));

        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        PickupVehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE));

        entity.setDistrict(district);
        entity.setVehicle(vehicle);
        entity.setIsActive(request.isActive());

        repository.save(entity);
    }
}
