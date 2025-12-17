package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCustomerAddressRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerAddressRequest;
import com.resustainability.recollect.dto.response.ICustomerAddressResponse;
import com.resustainability.recollect.service.CustomerAddressService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/address")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
public class CustomerAddressController {
    private final CustomerAddressService customerAddressService;

	@Autowired
    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<ICustomerAddressResponse>> list(
            @RequestParam(value = "customerId", required = false) Long customerId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                customerAddressService.list(customerId, searchCriteria)
        );
    }

    @GetMapping("/details/{customerAddressId}")
    public APIResponse<ICustomerAddressResponse> getById(
            @PathVariable(value = "customerAddressId", required = false) Long customerAddressId
    ) {
        return new APIResponse<>(
                customerAddressService.getById(customerAddressId)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(
            @RequestBody(required = false) AddCustomerAddressRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_CUSTOMER_ADDRESS,
                customerAddressService.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateCustomerAddressRequest request
    ) {
        customerAddressService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_CUSTOMER_ADDRESS_DETAILS);
    }

    @DeleteMapping("/delete/{customerAddressId}")
    public APIResponse<Void> deleteById(
            @PathVariable(value = "customerAddressId", required = false) Long customerAddressId
    ) {
        customerAddressService.deleteById(customerAddressId, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_CUSTOMER_ADDRESS);
    }
    
    @DeleteMapping("/un-delete/{customerAddressId}")
    public APIResponse<Void> undeleteById(
            @PathVariable(value = "customerAddressId", required = false) Long customerAddressId
    ) {
        customerAddressService.deleteById(customerAddressId, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_CUSTOMER_ADDRESS);
    }
}
