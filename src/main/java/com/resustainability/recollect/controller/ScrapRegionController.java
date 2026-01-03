package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddScrapRegionRequest;
import com.resustainability.recollect.dto.request.UpdateBorderRequest;
import com.resustainability.recollect.dto.request.UpdateScrapRegionRequest;
import com.resustainability.recollect.dto.response.IGeometryResponse;
import com.resustainability.recollect.dto.response.IScrapRegionAvailabilityResponse;
import com.resustainability.recollect.dto.response.IScrapRegionResponse;
import com.resustainability.recollect.dto.response.ScrapRegionResponse;
import com.resustainability.recollect.service.ScrapRegionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/scrap-region")
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'PROVIDER')")
public class ScrapRegionController {
    private final ScrapRegionService scrapRegionService;

    @Autowired
    public ScrapRegionController(
            ScrapRegionService scrapRegionService
    ) {
        this.scrapRegionService = scrapRegionService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IScrapRegionResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                scrapRegionService.list(searchCriteria)
        );
    }

    @GetMapping("/border/{scrapRegionId}")
    public APIResponse<IGeometryResponse> getBorderById(
            @PathVariable(value = "scrapRegionId", required = false) Long scrapRegionId
    ) {
        return new APIResponse<>(
                scrapRegionService.getBorderById(scrapRegionId),
                Default.SUCCESS,
                null
        );
    }

    @GetMapping("/list/available-dates/{scrapRegionId}")
    public APIResponse<List<IScrapRegionAvailabilityResponse>> listAvailableDates(
            @PathVariable(value = "scrapRegionId", required = false) Long scrapRegionId,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) LocalDate toDate
    ) {
        return new APIResponse<>(
                scrapRegionService.listAvailableDates(scrapRegionId, fromDate, toDate)
        );
    }

    @GetMapping("/details/{scrapRegionId}")
    public APIResponse<ScrapRegionResponse> getById(
            @PathVariable(value = "scrapRegionId", required = false) Long scrapRegionId
    ) {
        return new APIResponse<>(
                scrapRegionService.getById(scrapRegionId)
        );
    }

    @PostMapping("/add")
    public APIResponse<Void> add(
            @RequestBody(required = false) AddScrapRegionRequest request
    ) {
        scrapRegionService.add(request);
        return new APIResponse<>(Default.SUCCESS_ADD_SCRAP_REGION);
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateScrapRegionRequest request
    ) {
        scrapRegionService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_SCRAP_REGION_DETAILS);
    }

    @PatchMapping("/update-border")
    public APIResponse<Void> updateBorder(
            @RequestBody(required = false) UpdateBorderRequest request
    ) {
        scrapRegionService.updateBorder(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_SCRAP_REGION_BORDER);
    }

    @PatchMapping("/toggle/{scrapRegionId}")
    public APIResponse<Void> toggleById(
            @PathVariable(value = "scrapRegionId", required = false) Long scrapRegionId
    ) {
        scrapRegionService.toggleById(scrapRegionId);
        return new APIResponse<>(Default.SUCCESS_UPDATE_STATUS);
    }

    @DeleteMapping("/delete/{scrapRegionId}")
    public APIResponse<Void> deleteById(
            @PathVariable(value = "scrapRegionId", required = false) Long scrapRegionId
    ) {
        scrapRegionService.deleteById(scrapRegionId, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_SCRAP_REGION);
    }

    @DeleteMapping("/un-delete/{scrapRegionId}")
    public APIResponse<Void> undeleteById(
            @PathVariable(value = "scrapRegionId", required = false) Long scrapRegionId
    ) {
        scrapRegionService.deleteById(scrapRegionId, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_SCRAP_REGION);
    }
}
