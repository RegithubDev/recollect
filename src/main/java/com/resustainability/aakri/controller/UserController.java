package com.resustainability.aakri.controller;

import com.resustainability.aakri.dto.commons.APIResponse;
import com.resustainability.aakri.entity.backend.Customer;
import com.resustainability.aakri.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recollect/v1/user")
public class UserController {
    private final UserService userService;

	@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public APIResponse<List<Customer>> list() {
        return new APIResponse<>(
                userService.findAll()
        );
    }
}
