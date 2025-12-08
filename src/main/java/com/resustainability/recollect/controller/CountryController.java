package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddCountryRequest;
import com.resustainability.recollect.dto.request.UpdateCountryRequest;
import com.resustainability.recollect.dto.response.ICountryResponse;
import com.resustainability.recollect.service.CountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/country")
@PreAuthorize("hasRole('ADMIN')")
public class CountryController {
    private final CountryService countryService;

	@Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<ICountryResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                countryService.list(searchCriteria)
        );
    }

    @GetMapping("/details/{countryId}")
    public APIResponse<ICountryResponse> getById(
            @PathVariable(value = "countryId", required = false) Long countryId
    ) {
        return new APIResponse<>(
                countryService.getById(countryId)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(
            @RequestBody(required = false) AddCountryRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_COUNTRY,
                countryService.add(request)
        );
    }

    @PostMapping(value = "/upload-file/{countryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public APIResponse<String> uploadImage(
            @PathVariable(value = "countryId", required = false) Long countryId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return new APIResponse<>(
                countryService.uploadImage(countryId, file),
                Default.SUCCESS,
                null
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateCountryRequest request
    ) {
        countryService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_COUNTRY_DETAILS);
    }

    @DeleteMapping("/delete/{countryId}")
    public APIResponse<Void> deleteById(
            @PathVariable(value = "countryId", required = false) Long countryId
    ) {
        countryService.deleteById(countryId, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_COUNTRY);
    }
    
    @DeleteMapping("/un-delete/{countryId}")
    public APIResponse<Void> undeleteById(
            @PathVariable(value = "countryId", required = false) Long countryId
    ) {
        countryService.deleteById(countryId, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_COUNTRY);
    }

    @DeleteMapping(value = "/remove-file/{countryId}")
    public APIResponse<String> removeImage(
            @PathVariable(value = "countryId", required = false) Long countryId
    ) {
        return new APIResponse<>(
                countryService.removeImage(countryId),
                Default.SUCCESS,
                null
        );
    }
}
