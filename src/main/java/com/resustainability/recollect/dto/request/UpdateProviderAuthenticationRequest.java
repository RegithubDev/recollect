package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateProviderAuthenticationRequest(
        Long id,
        String otp
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);

        if (StringUtils.isNotBlank(otp)) {
            ValidationUtils.validateLength(otp, 6, 6, "OTP");
        }
    }
}
