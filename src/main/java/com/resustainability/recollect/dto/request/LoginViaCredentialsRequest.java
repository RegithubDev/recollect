package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record LoginViaCredentialsRequest(
        String username,
        String password,
        String fcmToken
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateUsername(username);
        ValidationUtils.validatePassword(password);
    }
}
