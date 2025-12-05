package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddPickupVehicleRequest;
import com.resustainability.recollect.dto.request.UpdatePickupVehicleRequest;
import com.resustainability.recollect.dto.response.IPickupVehicleResponse;
import com.resustainability.recollect.entity.backend.PickupVehicle;
import com.resustainability.recollect.entity.backend.State;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.PickupVehicleRepository;
import com.resustainability.recollect.repository.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class PickupVehicleService {

    private static final String FOLDER_NAME = "pickupvehicle";

    private final PickupVehicleRepository repository;
    private final StateRepository stateRepository;
    private final UploadService uploadService;

    @Autowired
    public PickupVehicleService(
            PickupVehicleRepository repository,
            StateRepository stateRepository,
            UploadService uploadService
    ) {
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.uploadService = uploadService;
    }

    public Pager<IPickupVehicleResponse> list(Long stateId, SearchCriteria sc) {
        return Pager.of(
                repository.findAllPaged(
                        sc.getQ(),
                        stateId,
                        sc.toPageRequest()
                )
        );
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IPickupVehicleResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByPickupVehicleId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE));
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddPickupVehicleRequest request) {

        ValidationUtils.validateRequestBody(request);

        if (repository.existsByVehicleNumber(request.vehicleNumber())) {
            throw new DataAlreadyExistException(
                    String.format("Vehicle number (%s) already exists", request.vehicleNumber())
            );
        }

        State state = stateRepository.findById(request.stateId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

        PickupVehicle entity = new PickupVehicle();
        entity.setVehicleName(request.vehicleName());
        entity.setVehicleNumber(request.vehicleNumber());
        entity.setPickupType(request.pickupType());
        entity.setCreatedOn(LocalDateTime.now());
        entity.setVehicleImage(null);
        entity.setIsAssigned(false);
        entity.setIsActive(true);
        entity.setIsDeleted(false);
        entity.setState(state);

        return repository.save(entity).getId();
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdatePickupVehicleRequest request) {

        ValidationUtils.validateRequestBody(request);

        PickupVehicle entity = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE));

        boolean numberUpdated =
                !entity.getVehicleNumber().equalsIgnoreCase(request.vehicleNumber());

        if (numberUpdated &&
                repository.existsByVehicleNumber(request.vehicleNumber())) {

            throw new DataAlreadyExistException(
                    String.format("Vehicle number (%s) already exists", request.vehicleNumber())
            );
        }

        entity.setVehicleName(request.vehicleName());
        entity.setVehicleNumber(request.vehicleNumber());
        entity.setPickupType(request.pickupType());
        entity.setIsActive(Boolean.TRUE.equals(request.isActive()));
        entity.setIsAssigned(Boolean.TRUE.equals(request.isAssigned()));

        if (request.stateId() != null) {
            State state = stateRepository.findById(request.stateId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));
            entity.setState(state);
        }

        repository.save(entity);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void delete(Long id, boolean value) {
        ValidationUtils.validateId(id);

        if (repository.deletePickupVehicleById(id, !value, value) == 0) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE);
        }
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String uploadImage(Long id, MultipartFile file) {

        ValidationUtils.validateId(id);
        ValidationUtils.validateMultipartSize(file, Default.MAX_IMAGE_FILE_SIZE);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE);
        }

        String filePath = uploadService.upload(FOLDER_NAME, file);

        if (repository.updateVehicleImageById(id, filePath) == 0) {
            uploadService.remove(filePath);
            throw new ResourceNotFoundException(Default.ERROR_UNABLE_TO_UPLOAD);
        }

        return filePath;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String removeImage(Long id) {

        ValidationUtils.validateId(id);

        String filePath = repository.findVehicleImageById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_PICKUP_VEHICLE));

        uploadService.remove(filePath);

        repository.updateVehicleImageById(id, null);

        return filePath;
    }
}
