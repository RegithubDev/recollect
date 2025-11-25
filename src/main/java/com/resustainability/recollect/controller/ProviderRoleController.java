package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderRoleRequest;
import com.resustainability.recollect.dto.request.UpdateProviderRoleRequest;
import com.resustainability.recollect.dto.response.IProviderRoleResponse;
import com.resustainability.recollect.service.ProviderRoleService;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/recollect/v1/provider-roles")
@PreAuthorize("hasRole('ADMIN')")
public class ProviderRoleController {

    private final ProviderRoleService service;

    public ProviderRoleController(ProviderRoleService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderRoleResponse>> list(@ModelAttribute SearchCriteria criteria) {
        return new APIResponse<>(service.list(criteria));
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderRoleResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderRoleRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_PROVIDERROLE, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderRoleRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROVIDERROLE);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.deleteOrUndelete(id, false);
        return new APIResponse<>(Default.SUCCESS_DELETE_PROVIDERROLE);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelele(@PathVariable Long id) {
        service.deleteOrUndelete(id, true);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_PROVIDERROLE);
    }
}
