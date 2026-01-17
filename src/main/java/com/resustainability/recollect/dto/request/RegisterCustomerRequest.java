package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record RegisterCustomerRequest(
        String name,
        String phoneNumber,
        String email,
        String userType,
        String platform,
        Long stateId,
        String fcmToken,
        Boolean isOptional
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateName(name);
        ValidationUtils.validatePhone(phoneNumber);
        ValidationUtils.validateEmail(email);
        ValidationUtils.validateUserType(userType);
        ValidationUtils.validatePlatform(platform);
        ValidationUtils.validateStateId(stateId);
    }
}
