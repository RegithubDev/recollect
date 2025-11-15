package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record LoginViaPhoneNumberRequest(
        String phoneNumber
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validatePhone(phoneNumber);
    }
}
