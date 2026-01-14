package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddStateRequest;
import com.resustainability.recollect.dto.request.UpdateStateRequest;
import com.resustainability.recollect.dto.response.IStateResponse;
import com.resustainability.recollect.service.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/state")
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IStateResponse>> list(
            @RequestParam(required = false) Long countryId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                stateService.list(countryId, searchCriteria)
        );
    }

    @GetMapping("/details/{stateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<IStateResponse> getById(
            @PathVariable(value = "stateId", required = false) Long stateId
    ) {
        return new APIResponse<>(
                stateService.getById(stateId)
        );
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Long> add(
            @RequestBody(required = false) AddStateRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_STATE,
                stateService.add(request)
        );
    }

    @PostMapping(value = "/upload-file/{stateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<String> uploadImage(
            @PathVariable(value = "stateId", required = false) Long stateId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return new APIResponse<>(
                stateService.uploadImage(stateId, file),
                Default.SUCCESS,
                null
        );
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateStateRequest request
    ) {
        stateService.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_STATE_DETAILS);
    }

    @DeleteMapping("/delete/{stateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Void> deleteById(
            @PathVariable(value = "stateId", required = false) Long stateId
    ) {
        stateService.deleteById(stateId,true);
        return new APIResponse<>(Default.SUCCESS_DELETE_STATE);
    }
    
    @DeleteMapping("/un-delete/{stateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Void> undeleteById(
            @PathVariable(value = "stateId", required = false) Long stateId
    ) {
        stateService.deleteById(stateId,false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_STATE);
    }

    @DeleteMapping("/remove-file/{stateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<String> removeImage(
            @PathVariable(value = "stateId", required = false) Long stateId
    ) {
        return new APIResponse<>(
                stateService.removeImage(stateId),
                Default.SUCCESS,
                null
        );
    }
}

