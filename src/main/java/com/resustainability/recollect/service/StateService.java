package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddStateRequest;
import com.resustainability.recollect.dto.request.UpdateStateRequest;
import com.resustainability.recollect.dto.response.IStateResponse;
import com.resustainability.recollect.entity.backend.Country;
import com.resustainability.recollect.entity.backend.State;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.CountryRepository;
import com.resustainability.recollect.repository.StateRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StateService {

    private static final String FOLDER_NAME = "state";

    private final UploadService uploadService;
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public StateService(
            UploadService uploadService,
            StateRepository stateRepository,
            CountryRepository countryRepository
    ) {
        this.uploadService = uploadService;
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }


    public Pager<IStateResponse> list(Long countryId, SearchCriteria searchCriteria) {
        return Pager.of(
            stateRepository.findAllPaged(
                searchCriteria.getQ(),
                countryId,
                searchCriteria.toPageRequest()
            )
        );
    }



    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IStateResponse getById(Long stateId) {
        ValidationUtils.validateId(stateId);
        return stateRepository
                .findByStateId(stateId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddStateRequest request) {

        ValidationUtils.validateRequestBody(request);

 
        if (stateRepository.existsByCode(request.code())) {
            throw new DataAlreadyExistException(
                    String.format("State with (%s) already exists", request.code())
            );
        }


        Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_COUNTRY));

        return stateRepository.save(
                new State(
                        null,
                        null, 
                        request.name(),
                        request.code(),
                        true,
                        false,
                        country
                )
        ).getId();
    }

    // UPDATE STATE DETAILS
//    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//    public void update(UpdateStateRequest request) {
//
//        ValidationUtils.validateRequestBody(request);
//
//        final State entity = stateRepository
//                .findById(request.id())
//                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));
//
//        boolean hasCodeUpdated = !entity.getStateCode().equalsIgnoreCase(request.code());
//
//        if (hasCodeUpdated && stateRepository.existsByCode(request.code())) {
//            throw new DataAlreadyExistException(
//                    String.format("State with (%s) already exists", request.code())
//            );
//        }
//
//        entity.setStateCode(request.code());
//        entity.setStateName(request.name());
//        entity.setActive(Boolean.TRUE.equals(request.isActive()));
//
//        // If country change is allowed
//        if (request.countryId() != null) {
//            Country country = countryRepository.findById(request.countryId())
//                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_COUNTRY));
//            entity.setCountry(country);
//        }
//
//        stateRepository.save(entity);
//    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateStateRequest request) {

        ValidationUtils.validateRequestBody(request);

        final State entity = stateRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

        boolean hasCodeUpdated = !entity.getStateCode().equalsIgnoreCase(request.code());

        if (hasCodeUpdated && stateRepository.existsByCode(request.code())) {
            throw new DataAlreadyExistException(
                    String.format("State with (%s) already exists", request.code())
            );
        }

        entity.setStateCode(request.code());
        entity.setStateName(request.name());

        if (Boolean.TRUE.equals(request.isActive())) {
            entity.setActive(true);
            entity.setDeleted(false);   // auto-reset
        }

        if (Boolean.FALSE.equals(request.isActive())) {
            entity.setActive(false);
            // do not force delete unless explicitly requested
        }

        if (request.isDeleted() != null && request.isDeleted()) {
            entity.setDeleted(true);
            entity.setActive(false);   // auto-reset
        }

        if (request.countryId() != null) {
            Country country = countryRepository.findById(request.countryId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_COUNTRY));
            entity.setCountry(country);
        }

        stateRepository.save(entity);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long stateId) {
        ValidationUtils.validateId(stateId);

        if (0 == stateRepository.deleteStateById(stateId)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE);
        }
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String uploadImage(Long stateId, MultipartFile file) {
        ValidationUtils.validateId(stateId);
        ValidationUtils.validateMultipartSize(file, Default.MAX_IMAGE_FILE_SIZE);

        if (!stateRepository.existsById(stateId)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE);
        }

        final String filePath = uploadService.upload(FOLDER_NAME, file);

        if (0 == stateRepository.updateStateImageById(stateId, filePath)) {
            uploadService.remove(filePath);
            throw new ResourceNotFoundException(Default.ERROR_UNABLE_TO_UPLOAD);
        }

        return filePath;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String removeImage(Long stateId) {
        ValidationUtils.validateId(stateId);

        final String filePath = stateRepository
                .findStateImageById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

        uploadService.remove(filePath);

        stateRepository.updateStateImageById(stateId, null);

        return filePath;
    }
}
