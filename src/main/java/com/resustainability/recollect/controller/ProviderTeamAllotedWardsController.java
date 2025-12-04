package com.resustainability.recollect.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderTeamAllotedWardRequest;
import com.resustainability.recollect.dto.request.UpdateProviderTeamAllotedWardRequest;
import com.resustainability.recollect.dto.response.IProviderTeamAllotedWardResponse;
import com.resustainability.recollect.service.ProviderTeamAllotedWardsService;

@RestController
@RequestMapping("/recollect/v1/provider-team-alloted-wards")
@PreAuthorize("hasRole('ADMIN')")
public class ProviderTeamAllotedWardsController {

    private final ProviderTeamAllotedWardsService service;

    public ProviderTeamAllotedWardsController(ProviderTeamAllotedWardsService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderTeamAllotedWardResponse>> list(@ModelAttribute SearchCriteria criteria) {
        return new APIResponse<>(service.list(criteria));
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderTeamAllotedWardResponse> details(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderTeamAllotedWardRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_PROVIDERTEAM_WARD, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderTeamAllotedWardRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROVIDERTEAM_WARD);
    }

}
