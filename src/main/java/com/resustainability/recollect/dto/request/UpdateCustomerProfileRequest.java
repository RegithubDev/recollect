package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateCustomerProfileRequest(
        String name,
        String phoneNumber,
        String email
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateName(name);
        ValidationUtils.validatePhone(phoneNumber);
        if (StringUtils.isNotBlank(email)) {
            ValidationUtils.validateEmail(email);
        }
    }
}
