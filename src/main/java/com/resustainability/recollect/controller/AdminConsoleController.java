package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.pagination.Pager;
import com.resustainability.recollect.dto.pagination.SearchCriteria;
import com.resustainability.recollect.dto.response.IAdminUserResponse;
import com.resustainability.recollect.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recollect/v1/admin-console")
public class AdminConsoleController {
    private final AdminService adminService;

    @Autowired
    public AdminConsoleController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/list")
    public APIResponse<Pager<IAdminUserResponse>> list(
            @ModelAttribute SearchCriteria searchCriteria
    ) {
        return new APIResponse<>(
                adminService.listAdminUsers(searchCriteria)
        );
    }

    @GetMapping("/details/{adminUserId}")
    public APIResponse<IAdminUserResponse> getById(
            @PathVariable(value = "adminUserId", required = false) Long adminUserId
    ) {
        return new APIResponse<>(
                adminService.getById(adminUserId)
        );
    }
}
