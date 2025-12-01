package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddProviderWardRequest(
        Long providerId,
        Long wardId,
        Boolean isActive
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateRequired(isActive, "Is Active");
        ValidationUtils.validateId(providerId);
        ValidationUtils.validateId(wardId);
    }
}
