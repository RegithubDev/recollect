package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddDistrictRequest;
import com.resustainability.recollect.dto.request.UpdateDistrictRequest;
import com.resustainability.recollect.dto.response.IDistrictResponse;
import com.resustainability.recollect.entity.backend.District;
import com.resustainability.recollect.entity.backend.State;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.DistrictRepository;
import com.resustainability.recollect.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;

    @Autowired
    public DistrictService(
            DistrictRepository districtRepository,
            StateRepository stateRepository
    ) {
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
    }

    public Pager<IDistrictResponse> list(Long stateId, Long countryId, SearchCriteria searchCriteria) {
        return Pager.of(
                districtRepository.findAllPaged(
                        searchCriteria.getQ(),
                        stateId,
                        countryId,
                        searchCriteria.toPageRequest()
                )
        );
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IDistrictResponse getById(Long districtId) {
        ValidationUtils.validateId(districtId);

        return districtRepository
                .findByDistrictId(districtId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));
    }

 
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddDistrictRequest request) {

        ValidationUtils.validateRequestBody(request);
        if(districtRepository.existsByName(request.name())) {
        	throw new DataAlreadyExistException(
                    String.format("District with name (%s) already exists", request.code())
            ); 
        }

        if (districtRepository.existsByCode(request.code())) {
            throw new DataAlreadyExistException(
                    String.format("District with code (%s) already exists", request.code())
            );
        }

        State state = stateRepository.findById(request.stateId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

      
        District entity = new District();
        entity.setDistrictName(request.name());
        entity.setDistrictCode(request.code());
        entity.setActive(true);
        entity.setDeleted(false);
        entity.setState(state);
        
        

        District saved = districtRepository.save(entity);
        return saved.getId();
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateDistrictRequest request) {

        ValidationUtils.validateRequestBody(request);

        District entity = districtRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT));

        
        boolean hasCodeUpdated = !entity.getDistrictCode().equalsIgnoreCase(request.code());

        if (hasCodeUpdated && districtRepository.existsByCode(request.code())) {
            throw new DataAlreadyExistException(
                    String.format("District with code (%s) already exists", request.code())
            );
        }
        
        entity.setDistrictCode(request.code());
        entity.setDistrictName(request.name());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));

        
        if (request.stateId() != null) {
            State state = stateRepository.findById(request.stateId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

            entity.setState(state);
          
        }

        districtRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long districtId, boolean value) {
        ValidationUtils.validateId(districtId);

        if (0 == districtRepository.deleteDistrictById(districtId, !value, value)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_DISTRICT);
        }
    }
}
