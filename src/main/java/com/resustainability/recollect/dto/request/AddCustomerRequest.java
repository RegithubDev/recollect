package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

import java.time.LocalDateTime;

public record AddCustomerRequest(
        String name,
        String phoneNumber,
        String email,
        String userType,
        String platform,
        Long districtId,
        Long scrapRegionId,
        Long stateId,
        Long wardId,
        Boolean isSuperuser,
        Boolean isStaff,
        LocalDateTime dateJoined
) implements RequestBodyValidator {
    @Override
    public void validate() {
        if (StringUtils.isNotBlank(name)) {
            ValidationUtils.validateName(name);
        }

        ValidationUtils.validatePhone(phoneNumber);

        if (StringUtils.isNotBlank(email)) {
            ValidationUtils.validateEmail(email);
        }

        if (StringUtils.isNotBlank(userType)) {
            ValidationUtils.validateUserType(userType);
        }

        if (StringUtils.isNotBlank(platform)) {
            ValidationUtils.validatePlatform(platform);
        }

        ValidationUtils.validateDateJoined(dateJoined);
    }
}
