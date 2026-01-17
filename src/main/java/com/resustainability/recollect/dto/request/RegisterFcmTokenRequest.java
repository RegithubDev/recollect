package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record RegisterFcmTokenRequest(
        String fcmToken,
        String platform,
        String deviceId
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateFcmToken(fcmToken);
        ValidationUtils.validatePlatform(platform);
        ValidationUtils.validateDeviceId(deviceId);
    }
}
