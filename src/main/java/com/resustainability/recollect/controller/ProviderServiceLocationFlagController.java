package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderServiceLocationFlagRequest;
import com.resustainability.recollect.dto.request.UpdateProviderServiceLocationFlagRequest;
import com.resustainability.recollect.dto.response.IProviderServiceLocationFlagResponse;
import com.resustainability.recollect.service.ProviderServiceLocationFlagService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider-service-location-flag")
public class ProviderServiceLocationFlagController {

    private final ProviderServiceLocationFlagService service;

    public ProviderServiceLocationFlagController(ProviderServiceLocationFlagService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderServiceLocationFlagResponse>> list(
            @RequestParam(required = false) Long providerId,
            @ModelAttribute SearchCriteria sc
    ) {
        return new APIResponse<>(service.list(providerId, sc));
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderServiceLocationFlagResponse> get(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderServiceLocationFlagRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_PROVIDER_LOCATION_FLAGS, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderServiceLocationFlagRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROVIDER_LOCATION_FLAGS);
    }
}
