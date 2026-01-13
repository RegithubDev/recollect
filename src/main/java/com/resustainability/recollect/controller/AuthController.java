package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.request.AddCustomerRequest;
import com.resustainability.recollect.dto.request.LoginViaCredentialsRequest;
import com.resustainability.recollect.dto.request.LoginViaPhoneNumberRequest;
import com.resustainability.recollect.dto.request.RegisterRequest;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.dto.response.TokenResponse;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.service.AuthService;
import com.resustainability.recollect.service.CustomerService;
import com.resustainability.recollect.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final SecurityService securityService;
    private final CustomerService customerService;

	@Autowired
    public AuthController(
            AuthService authService,
            SecurityService securityService,
            CustomerService customerService
    ) {
        this.authService = authService;
        this.securityService = securityService;
        this.customerService = customerService;
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'PROVIDER')")
    public APIResponse<IUserContext> self() {
        return new APIResponse<>(
                securityService
                        .getCurrentUser()
                        .orElseThrow(UnauthorizedException::new)
        );
    }

    @PostMapping("/get-customer-token")
    public APIResponse<TokenResponse> customerLogin(
            @RequestBody(required = false) LoginViaPhoneNumberRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                authService.customerLogin(request)
        );
    }

    @PostMapping("/get-provider-token")
    public APIResponse<TokenResponse> providerLogin(
            @RequestBody(required = false) LoginViaCredentialsRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                authService.providerLogin(request)
        );
    }

    @PostMapping("/get-admin-token")
    public APIResponse<TokenResponse> adminLogin(
            @RequestBody(required = false) LoginViaCredentialsRequest request
    ) {
        return new APIResponse<>(
                Default.SUCCESS,
                authService.adminLogin(request)
        );
    }
    
    
    @PostMapping("/register-customer")
    public APIResponse<Void> registerCustomer(
            @RequestBody(required = false) RegisterRequest request
    ) {
        customerService.registerCustomer(request);
        return new APIResponse<>(Default.SUCCESS_ADD_CUSTOMER);
    }
}
