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
    public WardService(WardRepository wardRepository, LocalBodyRepository localBodyRepository) {
        this.wardRepository = wardRepository;
        this.localBodyRepository = localBodyRepository;
    }


    public Pager<IWardResponse> list(Long localbodyId, Long districtId, Long stateId, Long countryId, SearchCriteria criteria) {
        return Pager.of(
                wardRepository.findAllPaged(
                        criteria.getQ(), localbodyId, districtId, stateId, countryId, criteria.toPageRequest()
                )
        );
    }


    @Transactional
    public IWardResponse getById(Long wardId) {

        ValidationUtils.validateId(wardId);

        return wardRepository.findWardById(wardId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));
    }


    @Transactional
    public Long add(AddWardRequest request) {

        ValidationUtils.validateRequestBody(request);

        LocalBody localbody = localBodyRepository.findById(request.localbodyId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));

        Ward ward = new Ward();
        ward.setWardNo(request.wardNo());
        ward.setWardName(request.wardName());
        ward.setWardWeekdayCurrent(request.wardWeekdayCurrent());
        ward.setWardWeekdayNext(request.wardWeekdayNext());
        ward.setActive(true);
        ward.setDeleted(false);
        ward.setLocalbody(localbody);

        return wardRepository.save(ward).getId();
    }


    @Transactional
    public void update(UpdateWardRequest request) {

        ValidationUtils.validateRequestBody(request);

        Ward entity = wardRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD));

        entity.setWardNo(request.wardNo());
        entity.setWardName(request.wardName());
        entity.setWardWeekdayCurrent(request.wardWeekdayCurrent());
        entity.setWardWeekdayNext(request.wardWeekdayNext());
        entity.setActive(Boolean.TRUE.equals(request.isActive()));

        LocalBody lb = localBodyRepository.findById(request.localbodyId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_LOCAL_BODY));

        entity.setLocalbody(lb);

        wardRepository.save(entity);
    }


    @Transactional
    public void deleteById(Long wardId, boolean value) {

        ValidationUtils.validateId(wardId);

        if (0 == wardRepository.deleteWardById(wardId, !value, value)) {
            throw new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WARD);
        }
    }
}
