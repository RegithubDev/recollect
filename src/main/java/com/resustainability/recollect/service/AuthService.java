package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.JwtUtil;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.LoginViaCredentialsRequest;
import com.resustainability.recollect.dto.request.LoginViaPhoneNumberRequest;
import com.resustainability.recollect.dto.response.TokenResponse;
import com.resustainability.recollect.entity.backend.AdminUser;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.entity.backend.Provider;
import com.resustainability.recollect.exception.BadCredentialsException;
import com.resustainability.recollect.tag.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final CustomerService customerService;
    private final AdminUserService adminUserService;
    private final ProviderService providerService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(
            CustomerService customerService,
            AdminUserService adminUserService,
            ProviderService providerService,
            JwtUtil jwtUtil
    ) {
        this.customerService = customerService;
        this.adminUserService = adminUserService;
        this.providerService = providerService;
        this.jwtUtil = jwtUtil;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public TokenResponse customerLogin(LoginViaPhoneNumberRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Customer customer = customerService
                .findByPhoneNumber(request.phoneNumber())
                .filter(usr -> !Boolean.TRUE.equals(usr.getDeleted()))
                .orElseThrow(BadCredentialsException::new);

        ValidationUtils.validateUserActiveStatus(customer::getActive);

        if (null == customer.getTokenAt()) {
            customer.setTokenAt(LocalDateTime.now());
            customerService.refreshTokenAtById(customer.getId());
        }

        customerService.refreshLastLoginAtById(customer.getId());

        return new TokenResponse(
                customer.getActive(),
                jwtUtil.generateToken(
                        customer.getPhoneNumber(),
                        Role.CUSTOMER.getAbbreviation(),
                        customer.getTokenAt(),
                        false
                ),
                customer.getFullName(),
                customer.getEmail(),
                customer.getUserType()
        );
    }

    public TokenResponse adminLogin(LoginViaCredentialsRequest request) {
        ValidationUtils.validateRequestBody(request);

        final AdminUser adminUser = adminUserService
                .findByUsernameAndPassword(request.username(), request.password())
                .filter(usr -> !Boolean.TRUE.equals(usr.getDeleted()))
                .orElseThrow(BadCredentialsException::new);

        ValidationUtils.validateUserActiveStatus(adminUser::getActive);

        if (null == adminUser.getTokenAt()) {
            adminUser.setTokenAt(LocalDateTime.now());
            adminUserService.refreshTokenAtById(adminUser.getId());
        }

        adminUserService.refreshLastLoginAtById(adminUser.getId());

        return new TokenResponse(
                adminUser.getActive(),
                jwtUtil.generateToken(
                        adminUser.getUsername(),
                        Role.ADMIN.getAbbreviation(),
                        adminUser.getTokenAt()
                ),
                adminUser.getFullName(),
                adminUser.getEmail(),
                Role.ADMIN.getAbbreviation()
        );
    }

    public TokenResponse providerLogin(LoginViaCredentialsRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Provider provider = providerService
                .findByUsernameAndPassword(request.username(), request.password())
                .filter(usr -> !Boolean.TRUE.equals(usr.getDeleted()))
                .orElseThrow(BadCredentialsException::new);

        ValidationUtils.validateUserActiveStatus(provider::getActive);

        if (null == provider.getTokenAt()) {
            provider.setTokenAt(LocalDateTime.now());
            providerService.refreshTokenAtById(provider.getId());
        }

        providerService.refreshLastLoginAtById(provider.getId());

        return new TokenResponse(
                provider.getActive(),
                jwtUtil.generateToken(
                        provider.getPhoneNumber(),
                        Role.PROVIDER.getAbbreviation(),
                        provider.getTokenAt()
                ),
                provider.getFullName(),
                provider.getEmail(),
                Role.PROVIDER.getAbbreviation()
        );
    }
}
