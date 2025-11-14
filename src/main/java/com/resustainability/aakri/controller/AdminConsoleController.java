package com.resustainability.aakri.controller;

import com.resustainability.aakri.dto.commons.APIResponse;
import com.resustainability.aakri.entity.backend.AdminUser;
import com.resustainability.aakri.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recollect/v1/admin-console")
public class AdminConsoleController {
    private final AdminService adminService;

    @Autowired
    public AdminConsoleController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/list")
    public APIResponse<List<AdminUser>> list() {
        return new APIResponse<>(
                adminService.findAll()
        );
    }
}
