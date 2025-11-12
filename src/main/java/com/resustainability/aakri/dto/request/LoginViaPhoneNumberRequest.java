package com.resustainability.aakri.dto.request;

import com.resustainability.aakri.commons.ValidationUtils;
import com.resustainability.aakri.dto.commons.RequestBodyValidator;

public record LoginViaPhoneNumberRequest(
        String phoneNumber
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validatePhone(phoneNumber);
    }
}
