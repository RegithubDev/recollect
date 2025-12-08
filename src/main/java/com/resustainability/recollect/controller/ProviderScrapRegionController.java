package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddProviderScrapRegionRequest;
import com.resustainability.recollect.dto.request.UpdateProviderScrapRegionRequest;
import com.resustainability.recollect.dto.response.IProviderScrapRegionResponse;
import com.resustainability.recollect.service.ProviderScrapRegionService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider-scrap-region")
public class ProviderScrapRegionController {

    private final ProviderScrapRegionService service;

    public ProviderScrapRegionController(ProviderScrapRegionService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IProviderScrapRegionResponse>> list(
            @RequestParam(required = false) Long providerId,
            @RequestParam(required = false) Long scrapRegionId,
            @ModelAttribute SearchCriteria sc
    ) {
        return new APIResponse<>(service.list(providerId, scrapRegionId, sc));
    }

    @GetMapping("/details/{id}")
    public APIResponse<IProviderScrapRegionResponse> get(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddProviderScrapRegionRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_PROVIDER_SCRAP_REGION, service.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateProviderScrapRegionRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_PROVIDER_SCRAP_REGION);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.deleteById(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_PROVIDER_SCRAP_REGION);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.deleteById(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_PROVIDER_SCRAP_REGION);
    }
}
