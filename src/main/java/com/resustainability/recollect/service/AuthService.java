package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.JwtUtil;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.request.LoginViaPhoneNumberRequest;
import com.resustainability.recollect.dto.response.TokenResponse;
import com.resustainability.recollect.entity.backend.Customer;
import com.resustainability.recollect.exception.BadCredentialsException;
import com.resustainability.recollect.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(
            CustomerRepository customerRepository,
            UserService userService,
            JwtUtil jwtUtil
    ) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public TokenResponse loginViaPhoneNumber(LoginViaPhoneNumberRequest request) {
        ValidationUtils.validateRequestBody(request);

        final Customer customer = userService
                .findByPhoneNumber(request.phoneNumber())
                .orElseThrow(BadCredentialsException::new);

        if (null == customer.getTokenAt()) {
            customer.setTokenAt(LocalDateTime.now());
            customerRepository.save(customer);
        }

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
