package com.resustainability.recollect.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.resustainability.recollect.dto.request.AddServiceCategoryRequest;
import com.resustainability.recollect.dto.request.UpdateServiceCategoryRequest;
import com.resustainability.recollect.dto.response.IServiceCategoryResponse;
import com.resustainability.recollect.service.ServiceCategoryService;

@RestController
@RequestMapping("/api/v1/service-category")
@PreAuthorize("hasRole('ADMIN')")
public class ServiceCategoryController {

    private final ServiceCategoryService service;

    @Autowired
    public ServiceCategoryController(ServiceCategoryService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IServiceCategoryResponse>> list(
            @ModelAttribute SearchCriteria criteria
    ) {
        return new APIResponse<>(
        		service.list(criteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IServiceCategoryResponse> get(@PathVariable Long id) {
        return new APIResponse<>(
        		service.getById(id)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddServiceCategoryRequest request) {
        return new APIResponse<>(
        		Default.SUCCESS, 
        		service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateServiceCategoryRequest request) {
        service.update(request);
        return new APIResponse<>(
        		Default.SUCCESS
        );
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.delete(id, true);
        return new APIResponse<>(
        		Default.SUCCESS
        );
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.delete(id, false);
        return new APIResponse<>(
        		Default.SUCCESS
        );
    }

    @PostMapping(value = "/upload-icon/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<String> uploadIcon(
            @PathVariable Long id,
            @RequestParam MultipartFile file
    ) {
        return new APIResponse<>(
        		service.uploadIcon(id, file)
        );
    }

    @DeleteMapping("/remove-icon/{id}")
    public APIResponse<String> removeIcon(@PathVariable Long id) {
        return new APIResponse<>(
        		service.removeIcon(id)
        );
    }
}
