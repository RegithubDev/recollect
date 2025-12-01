package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateProviderLocalBodyRequest(
        Long id,
        Long providerId,
        Long localBodyId,
        Boolean isActive
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateRequired(isActive, "Is Active");
        ValidationUtils.validateId(providerId);
        ValidationUtils.validateId(localBodyId);
    }
}
