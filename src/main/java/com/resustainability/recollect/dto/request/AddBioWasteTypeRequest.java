package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddBioWasteTypeRequest(
        String biowasteName,
        Long biowasteCategoryId
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateName(biowasteName);
        ValidationUtils.validateId(biowasteCategoryId);
    }
}
