package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.dto.response.ItemCategoryResponse;
import com.resustainability.recollect.service.MobileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mobile")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
public class MobileController {
    private final MobileService mobileService;

    @Autowired
    public MobileController(MobileService mobileService) {
        this.mobileService = mobileService;
    }

    @GetMapping("/list-pickup-services")
    public APIResponse<List<IServiceCategoryResponse>> listServices() {
        return new APIResponse<>(
                mobileService.listServices()
        );
    }

    @GetMapping("/list-bio-waste-categories")
    public APIResponse<Collection<ItemCategoryResponse>> listBioWasteCategories() {
        return new APIResponse<>(
                mobileService.listBioWasteCategories()
        );
    }

    @GetMapping("/list-scrap-categories")
    public APIResponse<Collection<ItemCategoryResponse>> listScrapCategories(
            @RequestParam(value = "districtId", required = false) Long districtId
    ) {
        return new APIResponse<>(
                mobileService.listScrapCategories(districtId)
        );
    }
}
