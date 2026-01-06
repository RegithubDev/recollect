package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.IAdminUserResponse;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.service.AdminUserService;
import com.resustainability.recollect.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin-console")
@PreAuthorize("hasRole('ADMIN')")
public class AdminConsoleController {
    private final AdminUserService adminUserService;
    private final SecurityService securityService;

    @Autowired
    public AdminConsoleController(
            AdminUserService adminUserService,
            SecurityService securityService
    ) {
        this.adminUserService = adminUserService;
        this.securityService = securityService;
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public APIResponse<IAdminUserResponse> self() {
        final Long userId = securityService
                .getCurrentUserId()
                .orElseThrow(UnauthorizedException::new);
        return new APIResponse<>(
                adminUserService.getById(userId)
        );
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
