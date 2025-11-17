package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.JwtUtil;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.LoginViaPhoneNumberRequest;
import com.resustainability.recollect.dto.response.TokenResponse;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.exception.BadCredentialsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(
            CustomerService customerService,
            JwtUtil jwtUtil
    ) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public TokenResponse loginViaPhoneNumber(LoginViaPhoneNumberRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Customer customer = customerService
                .findByPhoneNumber(request.phoneNumber())
                .orElseThrow(BadCredentialsException::new);

        if (null == customer.getTokenAt()) {
            customerService.refreshTokenAtById(customer.getId());
        }

        customerService.refreshLastLoginAtById(customer.getId());

        return new TokenResponse(
                customer.getActive(),
                jwtUtil.generateToken(
                        customer.getPhoneNumber(),
                        customer.getTokenAt()
                ),
                customer.getFullName(),
                customer.getEmail(),
                customer.getUserType()
        );
    }
}
