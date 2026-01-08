package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCustomerAddressRequest;
import com.resustainability.recollect.dto.request.UpdateCustomerAddressRequest;
import com.resustainability.recollect.dto.response.BoundariesResponse;
import com.resustainability.recollect.dto.response.ICustomerAddressResponse;
import com.resustainability.recollect.service.CustomerAddressService;

import java.util.List;
import java.util.Set;

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

    @GetMapping("/list-overlap-regions")
    public APIResponse<BoundariesResponse> listAllContainingGeometry(
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude
    ) {
        return new APIResponse<>(
                customerAddressService.listAllContainingGeometry(latitude, longitude)
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

    @GetMapping("/scrap-boundary/{scrapRegionId}")
    public APIResponse<Boolean> isInScrapRegionBoundaries(
            @PathVariable(value = "scrapRegionId") Long scrapRegionId,
            @PathVariable(value = "latitude") String latitude,
            @PathVariable(value = "longitude") String longitude
    ) {
        return new APIResponse<>(
                customerAddressService.isInScrapRegionBoundaries(latitude, longitude, scrapRegionId)
        );
    }

    @GetMapping("/bio-boundary/{localBodyId}")
    public APIResponse<Boolean> isInLocalBodyBoundaries(
            @PathVariable(value = "localBodyId") Long localBodyId,
            @RequestParam(value = "latitude") String latitude,
            @RequestParam(value = "longitude") String longitude
    ) {
        return new APIResponse<>(
                customerAddressService.isInLocalBodyBoundaries(latitude, longitude, localBodyId)
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
