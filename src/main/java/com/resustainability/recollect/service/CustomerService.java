package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.ICustomerResponse;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.exception.ResourceNotFoundException;
import com.resustainability.recollect.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(
            CustomerRepository customerRepository
    ) {
        this.customerRepository = customerRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Pager<ICustomerResponse> listCustomers(SearchCriteria searchCriteria) {
        return Pager.of(
                customerRepository.findAllPaged(
                        searchCriteria.getQ(),
                        searchCriteria.toPageRequest()
                )
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ICustomerResponse getById(Long customerId) {
        ValidationUtils.validateUserId(customerId);
        return customerRepository
                .findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(Default.ERROR_NOT_FOUND_USER));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int refreshTokenAtById(Long customerId) {
        return customerRepository.updateTokenAtById(customerId, LocalDateTime.now());
    }
}
