package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddScrapCategoryRequest;
import com.resustainability.recollect.dto.request.UpdateScrapCategoryRequest;
import com.resustainability.recollect.dto.response.IScrapCategoryResponse;
import com.resustainability.recollect.service.ScrapCategoryService;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/v1/scrap-category")
@PreAuthorize("hasRole('ADMIN')")
public class ScrapCategoryController {

    private final ScrapCategoryService service;

    public ScrapCategoryController(ScrapCategoryService service) {
        this.service = service;
    }

  /*  @GetMapping("/list")
    public APIResponse<Pager<IScrapCategoryResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.list(searchCriteria)
        );
    }*/
    
    @GetMapping("/list/scrap")
    public APIResponse<Pager<IScrapCategoryResponse>> listScrap(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.listScrap(searchCriteria)
        );
    }

    @GetMapping("/list/disposals")
    public APIResponse<Pager<IScrapCategoryResponse>> listDisposal(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.listDisposal(searchCriteria)
        );
    }

    
    
    @GetMapping("/details/{id}")
    public APIResponse<IScrapCategoryResponse> get(
    		@PathVariable Long id
    ) {
        return new APIResponse<>(
        		service.getById(id)
        );
    }


    @PostMapping("/add/scrap")
    public APIResponse<Long> addScrap(
            @RequestBody(required = false) AddScrapCategoryRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                service.addScrapCategory(request)
        );
    }

    
    @PostMapping("/add/disposal")
    public APIResponse<Long> addDisposal(
            @RequestBody(required = false) AddScrapCategoryRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                service.addDisposalCategory(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(
            @RequestBody(required = false) UpdateScrapCategoryRequest request
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
