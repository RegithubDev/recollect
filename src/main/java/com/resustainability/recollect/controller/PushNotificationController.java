package com.resustainability.recollect.controller;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.dto.commons.APIResponse;
import com.resustainability.recollect.dto.request.PushNotificationRequest;
import com.resustainability.recollect.dto.request.RegisterFcmTokenRequest;
import com.resustainability.recollect.service.PushNotificationService;
import com.resustainability.recollect.service.PushTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/push")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'PROVIDER')")
public class PushNotificationController {
    private final PushTokenService pushTokenService;
    private final PushNotificationService pushNotificationService;

    @Autowired
    public PushNotificationController(
            PushTokenService pushTokenService,
            PushNotificationService pushNotificationService
    ) {
        this.pushTokenService = pushTokenService;
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/register")
    public APIResponse<String> add(@RequestBody(required = false) RegisterFcmTokenRequest request) {
        pushTokenService.registerToken(request);
        return new APIResponse<>(Default.SUCCESS);
    }

    @PutMapping("/notification")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<String> add(@RequestBody(required = false) PushNotificationRequest request) {
        pushNotificationService.send(request);
        return new APIResponse<>(Default.SUCCESS);
    }
}
