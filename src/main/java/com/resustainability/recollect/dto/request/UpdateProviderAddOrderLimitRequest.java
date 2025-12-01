package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.exception.InvalidDataException;

public record UpdateProviderAddOrderLimitRequest(
        Long id,
        Integer maxLimit,
        Integer currentLimit,
        Long providerId
) implements RequestBodyValidator {

    @Override
    public void validate() {

        ValidationUtils.validateId(id);
        ValidationUtils.validateRequired(maxLimit, "Max Limit");
        ValidationUtils.validateRequired(currentLimit, "Current Limit");
        ValidationUtils.validateNonNegative(Long.valueOf(maxLimit), "Max Limit");
        ValidationUtils.validateNonNegative(Long.valueOf(currentLimit), "Current Limit");
        ValidationUtils.validateId(providerId);

        if (currentLimit > maxLimit) {
            throw new InvalidDataException("Current Limit cannot be greater than Max Limit");
        }
    }
}
