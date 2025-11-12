package com.resustainability.aakri.service;

import com.resustainability.aakri.commons.JwtUtil;
import com.resustainability.aakri.commons.ValidationUtils;
import com.resustainability.aakri.dto.request.LoginViaPhoneNumberRequest;
import com.resustainability.aakri.entity.UserEntity;
import com.resustainability.aakri.dto.response.CustomerTokenResponse;
import com.resustainability.aakri.exception.BadCredentialsException;

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

        final UserEntity user = userService
                .findByPhoneNumber(request.phoneNumber())
                .orElseThrow(BadCredentialsException::new);

        return new CustomerTokenResponse(
                "Success",
                true,
                user.isActive(),
                jwtUtil.generateAccessToken(user),
                jwtUtil.generateRefreshToken(user),
                user.getFullName(),
                user.getEmail(),
                user.getUserType()
        );
    }
}
