package com.resustainability.recollect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.request.UpdateCustomerRequest;
import com.resustainability.recollect.dto.response.ICustomerResponse;
import com.resustainability.recollect.service.CustomerService;

@RestController
@RequestMapping("/recollect/v1/customer")
public class CustomerSelfController {

    private final CustomerService customerService;

	@Autowired
    public CustomerSelfController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/self-details/{customerId}")
    public APIResponse<ICustomerResponse> getById(
            @PathVariable(value = "customerId", required = false) Long customerId
    ) {
        return new APIResponse<>(
                customerService.getById(customerId)
        );
    }
	
    @PutMapping("/self-update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateCustomerRequest request
    ) {
        customerService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_USER_DETAILS);
    }

}
