package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

import java.time.LocalDate;

public record PlaceOrderRequest(
        LocalDate scheduleDate,
        String altNumber,
        String platform,
        Long addressId
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateScheduleDate(scheduleDate);

        if (StringUtils.isNotBlank(altNumber)) {
            ValidationUtils.validateAltPhone(altNumber);
        }

        ValidationUtils.validatePlatform(platform);
        ValidationUtils.validateAddressId(addressId);
    }
}
