package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.ProviderCashCollectionResponse;
import com.resustainability.recollect.service.ProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider")
@PreAuthorize("hasAnyRole('PROVIDER', 'ADMIN')")
public class ProviderController {
    private final ProviderService providerService;

	@Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/list-cash-collection")
    public APIResponse<Pager<ProviderCashCollectionResponse>> listCashCollection(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                providerService.listCashCollection(searchCriteria)
        );
    }
}
