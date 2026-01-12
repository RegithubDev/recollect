package com.resustainability.recollect.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBioWasteTypeRequest;
import com.resustainability.recollect.dto.request.UpdateBioWasteTypeRequest;
import com.resustainability.recollect.dto.response.IBioWasteTypeResponse;
import com.resustainability.recollect.service.BioWasteTypeService;

@RestController
@RequestMapping("/api/v1/biowaste-type")
@PreAuthorize("hasRole('ADMIN')")
public class BioWasteTypeController {

    private final BioWasteTypeService service;

    public BioWasteTypeController(BioWasteTypeService service) {
        this.service = service;
    }

    @GetMapping("/list/{categoryId}")
    public APIResponse<Pager<IBioWasteTypeResponse>> list(
            @PathVariable Long categoryId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.listByCategory(categoryId, searchCriteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IBioWasteTypeResponse> get(@PathVariable Long id) {
        return new APIResponse<>(
                service.getById(id)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(
            @RequestBody(required = false) AddBioWasteTypeRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateBioWasteTypeRequest request
    ) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS);
    }

    @PostMapping(
            value = "/upload-image/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public APIResponse<String> uploadImage(
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) {
        return new APIResponse<>(
                service.uploadImage(id, file)
        );
    }

    @DeleteMapping("/remove-image/{id}")
    public APIResponse<String> removeImage(@PathVariable Long id) {
        return new APIResponse<>(
                service.removeImage(id)
        );
    }
}
