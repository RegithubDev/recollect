package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.response.BioWasteCategoryResponse;
import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.dto.response.ScrapCategoryResponse;
import com.resustainability.recollect.service.MobileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/recollect/v1/mobile")
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
    public APIResponse<Collection<BioWasteCategoryResponse>> listBioWasteCategories() {
        return new APIResponse<>(
                mobileService.listBioWasteCategories()
        );
    }

    @GetMapping("/list-scrap-categories")
    public APIResponse<Collection<ScrapCategoryResponse>> listScrapCategories() {
        return new APIResponse<>(
                mobileService.listScrapCategories()
        );
    }
}
