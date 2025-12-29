package com.resustainability.recollect.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import org.springframework.http.MediaType;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddBwgClientRequest;
import com.resustainability.recollect.dto.request.UpdateBwgClientRequest;
import com.resustainability.recollect.dto.response.IBwgClientResponse;
import com.resustainability.recollect.service.BwgClientService;

@RestController
@RequestMapping("/api/v1/bwg-client")
@PreAuthorize("hasRole('ADMIN')")
public class BwgClientController {

    private final BwgClientService service;

    @Autowired
    public BwgClientController(BwgClientService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IBwgClientResponse>> list(@ModelAttribute SearchCriteria criteria) {
        return new APIResponse<>(
        		service.list(criteria)
        );
    }

    @GetMapping("/details/{id}")
    public APIResponse<IBwgClientResponse> get(@PathVariable Long id) {
        return new APIResponse<>(
        		service.getById(id)
        );
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddBwgClientRequest request) {
        return new APIResponse<>(
        		Default.SUCCESS_ADD_BWG_CLIENT, 
        		service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateBwgClientRequest request) {
        service.update(request);
        return new APIResponse<>(
        		Default.SUCCESS_UPDATE_BWG_CLIENT
        );
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.delete(id, true);
        return new APIResponse<>(
        		Default.SUCCESS_DELETE_BWG_CLIENT
        );
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.delete(id, false);
        return new APIResponse<>(
        		Default.SUCCESS_UNDELETE_BWG_CLIENT
        );
    }

    @PostMapping(value = "/upload-contract/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<String> upload(
    		@PathVariable Long id,
    		@RequestParam MultipartFile file) {
        return new APIResponse<>(
        		service.uploadContract(id, file),
        		Default.SUCCESS_UPLOAD_BWG_CONTRACT,
        		null
        );
    }


    @DeleteMapping("/remove-contract/{id}")
    public APIResponse<String> remove(@PathVariable Long id) {
        return new APIResponse<>(
        		service.removeContract(id), 
        		Default.SUCCESS_REMOVE_BWG_CONTRACT ,
        		null
        );
    }
}
