package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderWardRequest;
import com.resustainability.recollect.dto.request.UpdateProviderWardRequest;
import com.resustainability.recollect.dto.response.IProviderWardResponse;
import com.resustainability.recollect.service.ProviderWardService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recollect/v1/provider-ward")
public class ProviderWardController {

    private final ProviderWardService service;

    public ProviderWardController(ProviderWardService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderWardResponse>> list(
            @RequestParam(required = false) Long providerId,
            @RequestParam(required = false) Long wardId,
            @ModelAttribute SearchCriteria sc
    ) {
        return new APIResponse<>(service.list(providerId, wardId, sc));
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderWardResponse> get(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderWardRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_PROVIDER_WARD, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderWardRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROVIDER_WARD);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.deleteById(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_PROVIDER_WARD);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.deleteById(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_PROVIDER_WARD);
    }
}
