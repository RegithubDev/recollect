package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.ICustomerResponse;
import com.resustainability.recollect.dto.response.ProviderCashCollectionResponse;
import com.resustainability.recollect.dto.response.ProviderResponse;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.service.ProviderService;

import com.resustainability.recollect.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider")
@PreAuthorize("hasAnyRole('PROVIDER', 'ADMIN')")
public class ProviderController {
    private final ProviderService providerService;
    private final SecurityService securityService;

	@Autowired
    public ProviderController(
            ProviderService providerService,
            SecurityService securityService
    ) {
        this.providerService = providerService;
        this.securityService = securityService;
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('PROVIDER')")
    public APIResponse<ProviderResponse> self() {
        final Long userId = securityService
                .getCurrentUserId()
                .orElseThrow(UnauthorizedException::new);
        return new APIResponse<>(
                providerService.getById(userId)
        );
    }

    @GetMapping("/list-cash-collection")
    public APIResponse<Pager<ProviderCashCollectionResponse>> listCashCollection(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                providerService.listCashCollection(searchCriteria)
        );
    }

    @GetMapping("/list")
    public APIResponse<Pager<ProviderResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                providerService.list(searchCriteria)
        );
    }
}
