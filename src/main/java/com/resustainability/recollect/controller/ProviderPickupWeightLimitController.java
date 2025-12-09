package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderPickupWeightLimitRequest;
import com.resustainability.recollect.dto.request.UpdateProviderPickupWeightLimitRequest;
import com.resustainability.recollect.dto.response.IProviderPickupWeightLimitResponse;
import com.resustainability.recollect.service.ProviderPickupWeightLimitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider-pickup-weight-limit")
@PreAuthorize("hasRole('ADMIN')")
public class ProviderPickupWeightLimitController {

    private final ProviderPickupWeightLimitService service;

    @Autowired
    public ProviderPickupWeightLimitController(
            ProviderPickupWeightLimitService service
    ) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderPickupWeightLimitResponse>> list(
            @RequestParam(required = false) Long providerId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.list(providerId, searchCriteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderPickupWeightLimitResponse> getById(
            @PathVariable Long id
    ) {
        return new APIResponse<>(
                service.getById(id)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(
            @RequestBody AddProviderPickupWeightLimitRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_PICKUP_WEIGHT_LIMIT,
                service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody UpdateProviderPickupWeightLimitRequest request
    ) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PICKUPWEIGHT_LIMIT);
    }
}
