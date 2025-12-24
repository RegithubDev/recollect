package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.IBwgClientRequestResponse;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.BwgClientRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BwgClientRequestService {

    private final BwgClientRequestRepository repository;

    public BwgClientRequestService(BwgClientRequestRepository repository) {
        this.repository = repository;
    }
    
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<IBwgClientRequestResponse> list(SearchCriteria searchCriteria) {
        return Pager.of(
                repository.findAllPaged(
                        searchCriteria.toPageRequest()
                )
        );
    }
    

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public IBwgClientRequestResponse getById(Long id) {
        ValidationUtils.validateId(id);

        return repository.findByRequestId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Default.ERROR_NOT_FOUND_BWG_CLIENT_REQUEST
                        )
                );
    }
}
