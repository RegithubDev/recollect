package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateProviderLocalBodyRequest;
import com.resustainability.recollect.dto.response.IProviderLocalBodyResponse;
import com.resustainability.recollect.service.ProviderLocalBodyService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider-localbody")
public class ProviderLocalBodyController {

    private final ProviderLocalBodyService service;

    public ProviderLocalBodyController(ProviderLocalBodyService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderLocalBodyResponse>> list(
            @RequestParam(required = false) Long providerId,
            @RequestParam(required = false) Long localBodyId,
            @ModelAttribute SearchCriteria sc
    ) {
        return new APIResponse<>(
                service.list(providerId, localBodyId, sc)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderLocalBodyResponse> get(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderLocalBodyRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_LOCALBODY, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderLocalBodyRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_LOCALBODY);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.deleteById(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_LOCALBODY);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.deleteById(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_LOCALBODY);
    }
}
