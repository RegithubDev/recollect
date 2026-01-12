package com.resustainability.recollect.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddScrapTypeRequest;
import com.resustainability.recollect.dto.request.UpdateScrapTypeRequest;
import com.resustainability.recollect.dto.response.IScrapTypeResponse;
import com.resustainability.recollect.service.ScrapTypeService;

@RestController
@RequestMapping("/api/v1/scrap-type")
@PreAuthorize("hasRole('ADMIN')")
public class ScrapTypeController {

    private final ScrapTypeService service;

    public ScrapTypeController(ScrapTypeService service) {
        this.service = service;
    }

    @GetMapping("/list/{categoryId}")
    public APIResponse<Pager<IScrapTypeResponse>> list(
            @PathVariable Long categoryId,
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.listByCategory(categoryId, searchCriteria)
        );
    }

    @GetMapping("/details/{scraptypeId}")
    public APIResponse<IScrapTypeResponse> get(@PathVariable Long id) {
        return new APIResponse<>(
        		service.getById(id)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(
            @RequestBody(required = false) AddScrapTypeRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateScrapTypeRequest request
    ) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS);
    }

    @PostMapping(
            value = "/upload-image/{scraptypeId}",
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

    @DeleteMapping("/remove-image/{scraptypeId}")
    public APIResponse<String> removeImage(@PathVariable Long id) {
        return new APIResponse<>(
        		service.removeImage(id)
        );
    }
}
