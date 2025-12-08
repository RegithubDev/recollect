package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCustomerRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerProfileRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerRequest;
import com.resustainability.recollect.dto.response.ICustomerResponse;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.service.CustomerService;
import com.resustainability.recollect.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@PreAuthorize("hasRole('ADMIN')")
public class CustomerController {
    private final CustomerService customerService;
    private final SecurityService securityService;

    @Autowired
    public CustomerController(
            CustomerService customerService,
            SecurityService securityService
    ) {
        this.customerService = customerService;
        this.securityService = securityService;
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public APIResponse<ICustomerResponse> self() {
        final Long userId = securityService
                .getCurrentUserId()
                .orElseThrow(UnauthorizedException::new);
        return new APIResponse<>(
                customerService.getById(userId)
        );
    }

    @GetMapping("/list")
    public APIResponse<Pager<ICustomerResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                customerService.listCustomers(searchCriteria)
        );
    }

    @GetMapping("/details/{customerId}")
    public APIResponse<ICustomerResponse> getById(
            @PathVariable(value = "customerId", required = false) Long customerId
    ) {
        return new APIResponse<>(
                customerService.getById(customerId)
        );
    }

    @PostMapping("/add")
    public APIResponse<Void> add(
            @RequestBody(required = false) AddCustomerRequest request
    ) {
        customerService.add(request);
        return new APIResponse<>(Default.SUCCESS_ADD_USER);
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateCustomerRequest request
    ) {
        customerService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_USER_DETAILS);
    }

    @PutMapping("/update-profile")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public APIResponse<Void> updateProfile(
            @RequestBody(required = false) UpdateCustomerProfileRequest request
    ) {
        customerService.updateProfile(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROFILE_DETAILS);
    }

    @DeleteMapping("/delete/{customerId}")
    public APIResponse<Void> deleteById(
            @PathVariable(value = "customerId", required = false) Long customerId
    ) {
        customerService.deleteById(customerId);
        return new APIResponse<>(Default.SUCCESS_DELETE_USER);
    }
}
