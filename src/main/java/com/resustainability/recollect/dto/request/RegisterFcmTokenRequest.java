package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record RegisterFcmTokenRequest(
        String token,
        String platform
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateToken(token);
        ValidationUtils.validatePlatform(platform);
    }
}
