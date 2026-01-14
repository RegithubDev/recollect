package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.ICustomerTermsAndConditionsResponse;
import com.resustainability.recollect.service.CustomerTermsAndConditionsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/terms-conditions")
@PreAuthorize("hasRole('ADMIN')")
public class CustomerTermsAndConditionsController {
    private final CustomerTermsAndConditionsService customerTermsAndConditionsService;

	@Autowired
    public CustomerTermsAndConditionsController(CustomerTermsAndConditionsService customerTermsAndConditionsService) {
        this.customerTermsAndConditionsService = customerTermsAndConditionsService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<ICustomerTermsAndConditionsResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                customerTermsAndConditionsService.list(searchCriteria)
        );
    }

    @GetMapping("/details/{customerId}")
    public APIResponse<ICustomerTermsAndConditionsResponse> getById(
            @PathVariable(value = "customerId", required = false) Long customerId
    ) {
        return new APIResponse<>(
                customerTermsAndConditionsService.getById(customerId)
        );
    }
}
