package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateBwgOrderUsedBagRequest(
        Long id,
        Long bagId,
        Integer numberOfBags
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateId(bagId);
        ValidationUtils.validateNonNegative(numberOfBags, "Number of Bags");
    }
}
