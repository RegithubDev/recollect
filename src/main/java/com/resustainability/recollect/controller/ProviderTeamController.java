package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderTeamRequest;
import com.resustainability.recollect.dto.request.UpdateProviderTeamRequest;
import com.resustainability.recollect.dto.response.IProviderTeamResponse;
import com.resustainability.recollect.service.ProviderTeamService;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/recollect/v1/provider-team")
@PreAuthorize("hasRole('ADMIN')")
public class ProviderTeamController {

    private final ProviderTeamService service;

    public ProviderTeamController(ProviderTeamService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderTeamResponse>> list(@ModelAttribute SearchCriteria criteria) {
        return new APIResponse<>(service.list(criteria));
    }

    @GetMapping("/details/{teamId}")
    public APIResponse<IProviderTeamResponse> getById(@PathVariable Long teamId) {
        return new APIResponse<>(service.getById(teamId));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderTeamRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_PROVIDERTEAM, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderTeamRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROVIDERTEAM);
    }

    @DeleteMapping("/delete/{teamId}")
    public APIResponse<Void> delete(@PathVariable Long teamId) {
        service.deleteById(teamId, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_PROVIDERTEAM);
    }

    @DeleteMapping("/un-delete/{teamId}")
    public APIResponse<Void> undelete(@PathVariable Long teamId) {
        service.deleteById(teamId, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_PROVIDERTEAM);
    }
}
