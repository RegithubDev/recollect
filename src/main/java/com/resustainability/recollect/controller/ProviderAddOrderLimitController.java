package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.request.AddProviderAddOrderLimitRequest;
import com.resustainability.recollect.dto.request.UpdateProviderAddOrderLimitRequest;
import com.resustainability.recollect.dto.response.IProviderAddOrderLimitResponse;
import com.resustainability.recollect.service.ProviderAddOrderLimitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recollect/v1/provider-order-limit")
@PreAuthorize("hasRole('ADMIN')")
public class ProviderAddOrderLimitController {

    private final ProviderAddOrderLimitService service;

    @Autowired
    public ProviderAddOrderLimitController(ProviderAddOrderLimitService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody(required = false) AddProviderAddOrderLimitRequest request) {
        return new APIResponse<>(
        		Default.SUCCESS_ADD_PROVIDER_ORDER_LIMIT, 
        		service.add(request));
    }


    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody(required = false) UpdateProviderAddOrderLimitRequest request) {
        service.update(request);
        return new APIResponse<>(
        		Default.SUCCESS_UPDATE_PROVIDER_ORDER_LIMIT);
    }

 
    @GetMapping("/details/{id}")
    public APIResponse<IProviderAddOrderLimitResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(
        		service.getById(id));
    }


    @GetMapping("/list")
    public APIResponse<Page<IProviderAddOrderLimitResponse>> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long providerId,
            Pageable pageable
    ) {
        return new APIResponse<>(service.list(search, providerId, pageable));
    }
}
