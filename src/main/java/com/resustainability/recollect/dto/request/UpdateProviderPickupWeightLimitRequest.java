package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateProviderPickupWeightLimitRequest(
        Long id,
        Double weightLimit
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateRequired(weightLimit, "Weight Limit");
        ValidationUtils.validateNonNegative(weightLimit, "Weight Limit");
    }
}
