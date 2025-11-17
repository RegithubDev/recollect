package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCustomerRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerRequest;
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

    @DeleteMapping("/delete/{customerId}")
    public APIResponse<Void> deleteById(
            @PathVariable(value = "customerId", required = false) Long customerId
    ) {
        customerService.deleteById(customerId);
        return new APIResponse<>(Default.SUCCESS_DELETE_USER);
    }
}
