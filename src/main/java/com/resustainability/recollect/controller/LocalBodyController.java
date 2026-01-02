package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddLocalBodyRequest;
import com.resustainability.recollect.dto.request.UpdateBorderRequest;
import com.resustainability.recollect.dto.request.UpdateLocalBodyRequest;
import com.resustainability.recollect.dto.response.*;
import com.resustainability.recollect.service.LocalBodyService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/localbody")
@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'PROVIDER')")
public class LocalBodyController {

    private final LocalBodyService localBodyService;

    public LocalBodyController(LocalBodyService localBodyService) {
        this.localBodyService = localBodyService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<ILocalBodyResponse>> list(
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long countryId,
            @ModelAttribute SearchCriteria criteria
    ) {
        return new APIResponse<>(
                localBodyService.list(districtId, stateId, countryId, criteria)
        );
    }

    @GetMapping("/border/{localBodyId}")
    public APIResponse<IGeometryResponse> getBorderById(
            @PathVariable(value = "localBodyId", required = false) Long localBodyId
    ) {
        return new APIResponse<>(
                localBodyService.getBorderById(localBodyId),
                Default.SUCCESS,
                null
        );
    }

    @GetMapping("/list/available-dates/{localBodyId}")
    public APIResponse<List<ILocalBodyLimitResponse>> listAvailableDates(
            @PathVariable(value = "localBodyId", required = false) Long localBodyId,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) LocalDate toDate
    ) {
        return new APIResponse<>(
                localBodyService.listAvailableDates(localBodyId, fromDate, toDate)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<LocalBodyResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(localBodyService.getById(id));
    }
    
    @GetMapping("/details-localbody/{districtId}")
    public APIResponse<Pager<ILocalBodyResponseByDistrictId>> listByDistrict(
            @PathVariable Long districtId,
            @ModelAttribute SearchCriteria criteria
    ) {
        return new APIResponse<>(
                localBodyService.listByDistrict(districtId, criteria)
        );
    }
  

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddLocalBodyRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_LOCAL_BODY, localBodyService.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateLocalBodyRequest request) {
        localBodyService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_LOCAL_BODY);
    }

    @PatchMapping("/update-border")
    public APIResponse<Void> updateBorder(
            @RequestBody(required = false) UpdateBorderRequest request
    ) {
        localBodyService.updateBorder(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_LOCAL_BODY_BORDER);
    }

    @PatchMapping("/toggle/{localBodyId}")
    public APIResponse<Void> toggleById(
            @PathVariable(value = "localBodyId", required = false) Long localBodyId
    ) {
        localBodyService.toggleById(localBodyId);
        return new APIResponse<>(Default.SUCCESS_UPDATE_STATUS);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        localBodyService.deleteById(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_LOCAL_BODY);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        localBodyService.deleteById(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_LOCAL_BODY);
    }
}
