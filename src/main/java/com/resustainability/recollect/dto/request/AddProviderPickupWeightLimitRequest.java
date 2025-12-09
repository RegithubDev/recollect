package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddProviderPickupWeightLimitRequest(
        Long providerId,
        Double weightLimit
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(providerId);
        ValidationUtils.validateRequired(weightLimit, "Weight Limit");
        ValidationUtils.validateNonNegative(weightLimit, "Weight Limit");
    }
}
