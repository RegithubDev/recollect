package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddWardRequest;
import com.resustainability.recollect.dto.request.UpdateWardRequest;
import com.resustainability.recollect.dto.response.IWardResponse;
import com.resustainability.recollect.entity.backend.*;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

@Service
public class WardService {
    private final WardRepository wardRepository;
    private final LocalBodyRepository localBodyRepository;

    @Autowired
    public WardService(
            WardRepository wardRepository,
            LocalBodyRepository localBodyRepository
    ) {
        this.wardRepository = wardRepository;
        this.localBodyRepository = localBodyRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IWardResponse> list(Long localBodyId, Long districtId, Long stateId, Long countryId, SearchCriteria criteria) {
        return Pager.of(
                wardRepository.findAllPaged(
                        localBodyId, districtId, stateId, countryId,
                        criteria.getQ(),
                        criteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IWardResponse getById(Long wardId) {
        ValidationUtils.validateId(wardId);
        return wardRepository
                .findWardById(wardId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddWardRequest request) {
        ValidationUtils.validateRequestBody(request);

        final LocalBody localbody = localBodyRepository
                .findById(request.localBodyId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));

        return wardRepository.save(
                new Ward(
                        null,
                        request.wardNo(),
                        request.name(),
                        request.currentWeekDay(),
                        request.nextWeekDay(),
                        true,
                        false,
                        localbody
                )
        ).getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateWardRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Ward entity = wardRepository
                .findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));

        LocalBody localBody = localBodyRepository
                .findById(request.localBodyId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));

        entity.setWardNo(request.wardNo());
        entity.setWardName(request.name());
        entity.setWardWeekdayCurrent(request.currentWeekDay());
        entity.setWardWeekdayNext(request.nextWeekDay());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));
        entity.setLocalbody(localBody);

        wardRepository.save(entity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteById(Long wardId, boolean value) {
        ValidationUtils.validateId(wardId);
        if (0 == wardRepository.deleteWardById(wardId, !value, value)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void toggleById(Long wardId) {
        ValidationUtils.validateId(wardId);

        if (0 == wardRepository.toggleActiveStatusById(wardId)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
        }
    }
}
