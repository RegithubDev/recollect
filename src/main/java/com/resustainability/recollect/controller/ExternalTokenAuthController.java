package com.resustainability.recollect.controller;

import com.resustainability.recollect.dto.response.ExternalTokenResponse;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.service.SecurityService;
import com.resustainability.recollect.tag.KYCStatus;
import com.resustainability.recollect.tag.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verifying")
public class ExternalTokenAuthController {
    private final SecurityService securityService;

	@Autowired
    public ExternalTokenAuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/token")
    public ExternalTokenResponse validate() {
        final IUserContext user = securityService
                .getCurrentUser()
                .orElseThrow(UnauthorizedException::new);
        return new ExternalTokenResponse(
                true,
                "Token is valid",
                new ExternalTokenResponse.ValidData(
                        String.valueOf(user.getId()),
                        Role.fromUserContext(user),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        KYCStatus.FULL_KYC
                )
        );
    }
}
