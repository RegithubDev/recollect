package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddProviderAuthenticationRequest(
        Long providerId,
        String otp
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(providerId);
        ValidationUtils.validateRequired(otp, "OTP");
        ValidationUtils.validateLength(otp, 6, 6, "OTP");
    }
}
