package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.request.LoginViaPhoneNumberRequest;
import com.resustainability.recollect.dto.response.TokenResponse;
import com.resustainability.recollect.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recollect/v1/auth")
public class AuthController {
    private final AuthService authService;

	@Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/get-customer-token")
    public APIResponse<TokenResponse> loginViaPhoneNumber(
            @RequestBody(required = false) LoginViaPhoneNumberRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                authService.loginViaPhoneNumber(request)
        );
    }
}
