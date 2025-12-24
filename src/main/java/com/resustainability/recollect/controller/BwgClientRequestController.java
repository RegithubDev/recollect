package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.IBwgClientRequestResponse;
import com.resustainability.recollect.service.BwgClientRequestService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bwg-client-request")
@PreAuthorize("hasRole('ADMIN')")
public class BwgClientRequestController {

    private final BwgClientRequestService service;

    public BwgClientRequestController(BwgClientRequestService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IBwgClientRequestResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                service.list(searchCriteria)
        );
    }
    @GetMapping("/details/{id}")
    public APIResponse<IBwgClientRequestResponse> getById(
            @PathVariable Long id
    ) {
        return new APIResponse<>(service.getById(id));
    }
}
