package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.IAdminUserResponse;
import com.resustainability.recollect.service.AdminUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin-console")
@PreAuthorize("hasRole('ADMIN')")
public class AdminConsoleController {
    private final AdminUserService adminUserService;

    @Autowired
    public AdminConsoleController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IAdminUserResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                adminUserService.listAdminUsers(searchCriteria)
        );
    }

    @GetMapping("/details/{adminUserId}")
    public APIResponse<IAdminUserResponse> getById(
            @PathVariable(value = "adminUserId", required = false) Long adminUserId
    ) {
        return new APIResponse<>(
                adminUserService.getById(adminUserId)
        );
    }
}
