package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.request.AddProviderAuthenticationRequest;
import com.resustainability.recollect.dto.request.UpdateProviderAuthenticationRequest;
import com.resustainability.recollect.dto.response.IProviderAuthenticationResponse;
import com.resustainability.recollect.service.ProviderAuthenticationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/provider-auth")
@PreAuthorize("hasRole('ADMIN')")
public class ProviderAuthenticationController {

    private final ProviderAuthenticationService service;

    public ProviderAuthenticationController(ProviderAuthenticationService service) {
        this.service = service;
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderAuthenticationResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderAuthenticationRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_PROVIDER_AUTHENTICATION, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderAuthenticationRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROVIDER_AUTHENTICATION);
    }
}
