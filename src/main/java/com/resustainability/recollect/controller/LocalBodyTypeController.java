package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.request.AddLocalBodyTypeRequest;
import com.resustainability.recollect.dto.request.UpdateLocalBodyTypeRequest;
import com.resustainability.recollect.dto.response.ILocalBodyTypeResponse;
import com.resustainability.recollect.service.LocalBodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recollect/v1/local-body-type")
@PreAuthorize("hasRole('ADMIN')")
public class LocalBodyTypeController {

    private final LocalBodyTypeService service;

    @Autowired
    public LocalBodyTypeController(LocalBodyTypeService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<ILocalBodyTypeResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(service.list(searchCriteria));
    }

    @GetMapping("/details/{id}")
    public APIResponse<ILocalBodyTypeResponse> getById(@PathVariable Long id) {
        return new APIResponse<>(service.getById(id));
    }

    @PostMapping("/add")
    public APIResponse<Long> add(@RequestBody AddLocalBodyTypeRequest request) {
        return new APIResponse<>(
                Default.SUCCESS_ADD_LOCALBODYTYPE,
                service.add(request)
        );
    }

    @PutMapping("/update")
    public APIResponse<Void> update(@RequestBody UpdateLocalBodyTypeRequest request) {
        service.update(request);
        return new APIResponse<>(Default.SUCCESS_UPDATE_LOCALBODYTYPE);
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        service.deleteById(id, true);
        return new APIResponse<>(Default.SUCCESS_DELETE_LOCALBODYTYPE);
    }

    @DeleteMapping("/un-delete/{id}")
    public APIResponse<Void> undelete(@PathVariable Long id) {
        service.deleteById(id, false);
        return new APIResponse<>(Default.SUCCESS_UNDELETE_LOCALBODYTYPE);
    }
}
