package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddWardRequest;
import com.resustainability.recollect.dto.request.UpdateWardRequest;
import com.resustainability.recollect.dto.response.IWardResponse;
import com.resustainability.recollect.service.WardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ward")
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'PROVIDER')")
public class WardController {
    private final WardService service;

    @Autowired
    public WardController(WardService service) {
        this.service = service;
    }


    @GetMapping("/list")
    public APIResponse<Pager<IWardResponse>> list(
            @RequestParam(value = "localBodyId", required = false) Long localBodyId,
            @RequestParam(value = "districtId", required = false) Long districtId,
            @RequestParam(value = "stateId", required = false) Long stateId,
            @RequestParam(value = "countryId", required = false) Long countryId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.list(
                        localBodyId,
                        districtId,
                        stateId,
                        countryId,
                        searchCriteria
                )
        );
    }


    @GetMapping("/details/{wardId}")
    public APIResponse<IWardResponse> getById(@PathVariable Long wardId) {
        return new APIResponse<>(
                service.getById(wardId)
        );
    }


    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody(required = false) AddWardRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_WARD,
                service.add(request)
        );
    }


    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody(required = false) UpdateWardRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_WARD);
    }


    @PatchMapping("/toggle/{wardId}")
    public APIResponse<Void> toggleById(
            @PathVariable(value = "wardId", required = false) Long wardId
    ) {
        service.toggleById(wardId);
        return new APIResponse<>(Default.SUCCESS_UPDATE_STATUS);
    }


    @DeleteMapping("/delete/{wardId}")
    public APIResponse<Void> delete(@PathVariable(value = "wardId", required = false) Long wardId) {
        service.deleteById(wardId, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_WARD);
    }


    @DeleteMapping("/un-delete/{wardId}")
    public APIResponse<Void> undelete(@PathVariable(value = "wardId", required = false) Long wardId) {
        service.deleteById(wardId, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_WARD);
    }
}
