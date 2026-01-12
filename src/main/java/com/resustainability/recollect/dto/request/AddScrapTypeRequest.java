package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddScrapTypeRequest(
        String scrapName,
        Boolean isPayable,
        Boolean isKg,
        Long scrapCategoryId
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateName(scrapName);
        ValidationUtils.validateId(scrapCategoryId);
    }
}
