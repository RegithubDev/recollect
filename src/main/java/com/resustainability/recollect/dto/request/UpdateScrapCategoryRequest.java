package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateScrapCategoryRequest(
        Long id,
        String categoryName,
        String subcategoryName,
        Boolean isActive,
        String hsnCode
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateName(categoryName);
        ValidationUtils.validateCode(subcategoryName);
    }
}
