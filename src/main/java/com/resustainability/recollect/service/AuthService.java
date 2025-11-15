package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.JwtUtil;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.LoginViaPhoneNumberRequest;
import com.resustainability.recollect.dto.response.CustomerTokenResponse;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.exception.BadCredentialsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(
            UserService userService,
            JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public CustomerTokenResponse loginViaPhoneNumber(LoginViaPhoneNumberRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Customer customer = userService
                .findByPhoneNumber(request.phoneNumber())
                .orElseThrow(BadCredentialsException::new);

        return new CustomerTokenResponse(
                "Success",
                true,
                customer.getActive(),
                jwtUtil.generateAccessToken(customer),
                jwtUtil.generateRefreshToken(customer),
                customer.getFullName(),
                customer.getEmail(),
                customer.getUserType()
        );
    }
}
