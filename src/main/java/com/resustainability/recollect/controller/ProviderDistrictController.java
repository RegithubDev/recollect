package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderDistrictRequest;
import com.resustainability.recollect.dto.request.UpdateProviderDistrictRequest;
import com.resustainability.recollect.dto.response.IProviderDistrictResponse;
import com.resustainability.recollect.service.ProviderDistrictService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider-district")
public class ProviderDistrictController {

    private final ProviderDistrictService service;

    public ProviderDistrictController(ProviderDistrictService service) {
        this.service = service;
    }


    @GetMapping("/list")
    public APIResponse<Pager<IProviderDistrictResponse>> list(
            @RequestParam(required = false) Long providerId,
            @RequestParam(required = false) Long districtId,
            @ModelAttribute SearchCriteria sc
    ) {
        return new APIResponse<>(
                service.list(providerId, districtId, sc)
        );
    }


    @GetMapping("/details/{id}")
    public APIResponse<IProviderDistrictResponse> get(
            @PathVariable Long id
    ) {
        return new APIResponse<>(service.getById(id));
    }


    @PostMapping("/add")
    public APIResponse<Long> add(
            @RequestBody AddProviderDistrictRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_DISTRICT,
                service.add(request)
        );
    }


    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody UpdateProviderDistrictRequest request
    ) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_DISTRICT);
    }


    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.deleteById(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_DISTRICT);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.deleteById(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_DISTRICT);
    }
}
