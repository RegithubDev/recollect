package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddWasteBagRequest;
import com.resustainability.recollect.dto.request.UpdateWasteBagRequest;
import com.resustainability.recollect.dto.response.IWasteBagResponse;
import com.resustainability.recollect.entity.backend.State;
import com.resustainability.recollect.entity.backend.WasteBag;
import com.resustainability.recollect.exception.DataAlreadyExistException;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.StateRepository;
import com.resustainability.recollect.repository.WasteBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WasteBagService {

    private final WasteBagRepository wasteBagRepository;
    private final StateRepository stateRepository;

    @Autowired
    public WasteBagService(WasteBagRepository wasteBagRepository, StateRepository stateRepository) {
        this.wasteBagRepository = wasteBagRepository;
        this.stateRepository = stateRepository;
    }

    public Pager<IWasteBagResponse> list(Long stateId, SearchCriteria searchCriteria) {
        return Pager.of(
                wasteBagRepository.findAllPaged(
                        searchCriteria.getQ(),
                        stateId,
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IWasteBagResponse getById(Long id) {
        ValidationUtils.validateId(id);
        return wasteBagRepository.findByWasteBagId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WASTEBAG));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Long add(AddWasteBagRequest request) {
        ValidationUtils.validateRequestBody(request);

        if (wasteBagRepository.existsByBagSizeAndState(request.bagSize(), request.stateId())) {
            throw new DataAlreadyExistException(
                    String.format("WasteBag with size (%s) already exists for this state", request.bagSize())
            );
        }

        State state = stateRepository.findById(request.stateId())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));

        WasteBag entity = new WasteBag();
        entity.setBagSize(request.bagSize());
        entity.setBagPrice(request.bagPrice());
        entity.setBagCgst(request.bagCgst());
        entity.setBagSgst(request.bagSgst());
        entity.setState(state);
        entity.setIsActive(true);
        entity.setIsBwg(request.isBwg());

        WasteBag saved = wasteBagRepository.save(entity);
        return saved.getId();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void update(UpdateWasteBagRequest request) {
        ValidationUtils.validateRequestBody(request);

        WasteBag entity = wasteBagRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_WASTEBAG));

        entity.setBagSize(request.bagSize());
        entity.setBagPrice(request.bagPrice());
        entity.setBagCgst(request.bagCgst());
        entity.setBagSgst(request.bagSgst());
        entity.setIsActive(request.isActive());
        entity.setIsBwg(request.isBwg());

        if (request.stateId() != null) {
            State state = stateRepository.findById(request.stateId())
                    .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_STATE));
            entity.setState(state);
        }

        wasteBagRepository.save(entity);
    }
}
