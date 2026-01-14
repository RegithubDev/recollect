package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.ICustomerTermsAndConditionsResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerTermsAndConditionsService {
    private final SecurityService securityService;
    private final CustomerTermsAndConditionsRepository customerTermsAndConditionsRepository;

    @Autowired
    public CustomerTermsAndConditionsService(
            SecurityService securityService,
            CustomerTermsAndConditionsRepository customerTermsAndConditionsRepository
    ) {
        this.securityService = securityService;
        this.customerTermsAndConditionsRepository = customerTermsAndConditionsRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ICustomerTermsAndConditionsResponse> list(SearchCriteria searchCriteria) {
        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);

        final Pageable pageable = searchCriteria.toPageRequest();

        if (Boolean.TRUE.equals(user.getIsAdmin())) {
            return Pager.of(
                    customerTermsAndConditionsRepository.findAllPaged(
                            searchCriteria.getStart(),
                            searchCriteria.getEnd(),
                            searchCriteria.getQ(),
                            pageable
                    )
            );
        }

        return Pager.empty(pageable);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ICustomerTermsAndConditionsResponse getById(Long customerId) {
        ValidationUtils.validateId(customerId);

        securityService
                .getCurrentUser()
                .filter(usr -> Boolean.TRUE.equals(usr.getIsAdmin()))
                .orElseThrow(UnauthorizedException::new);

        return customerTermsAndConditionsRepository
                .findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_CUSTOMER));
    }
}
