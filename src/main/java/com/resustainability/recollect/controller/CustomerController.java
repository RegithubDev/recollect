package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.ICustomerResponse;
import com.resustainability.recollect.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recollect/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

	@Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
}
