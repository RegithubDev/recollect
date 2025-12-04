package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record CancelOrderRequest(
        Long id,
        Long reasonId
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateOrderId(id);
        ValidationUtils.validateReasonId(reasonId);
    }
}
