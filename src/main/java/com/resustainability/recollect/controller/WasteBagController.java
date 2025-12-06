package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddWasteBagRequest;
import com.resustainability.recollect.dto.request.UpdateWasteBagRequest;
import com.resustainability.recollect.dto.response.IWasteBagResponse;
import com.resustainability.recollect.service.WasteBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recollect/v1/wastebag")
@PreAuthorize("hasRole('ADMIN')")
public class WasteBagController {

    private final WasteBagService wasteBagService;

    @Autowired
    public WasteBagController(WasteBagService wasteBagService) {
        this.wasteBagService = wasteBagService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IWasteBagResponse>> list(
            @RequestParam(required = false) Long stateId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(wasteBagService.list(stateId, searchCriteria));
    }

    @GetMapping("/details/{id}")
    public APIResponse<IWasteBagResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(wasteBagService.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddWasteBagRequest request) {
        return new APIResponse<>(Default.SUCCESS_ADD_WASTEBAG, wasteBagService.add(request));
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateWasteBagRequest request) {
        wasteBagService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_WASTEBAG);
    }
}
