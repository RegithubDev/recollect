package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record RegisterRequest(
        String name,
        String phoneNumber,
        String email,
        String userType,
        String platform,
        Long districtId
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateName(name);
        ValidationUtils.validatePhone(phoneNumber);
        ValidationUtils.validateEmail(email);
        ValidationUtils.validateUserType(userType);
        ValidationUtils.validatePlatform(platform);
        ValidationUtils.validateDistrictId(districtId);
    }
}
