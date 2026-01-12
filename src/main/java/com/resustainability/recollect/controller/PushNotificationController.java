package com.resustainability.recollect.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/push")
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN', 'PROVIDER')")
public class PushNotificationController {
}
