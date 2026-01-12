package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record PushNotificationRequest(
        String token,
        String title,
        String body,
        String image
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateToken(token);
        ValidationUtils.validateRequired(title, "title");
    }
}
