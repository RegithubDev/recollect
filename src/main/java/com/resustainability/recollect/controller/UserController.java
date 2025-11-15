package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.service.UserService;

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
