package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateServiceCategoryRequest(
        Long id,
        String name,
        String title,
        String subtitle,
        Boolean isActive,
        String categoryUrl,
        String orderUrl
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateName(name);
        ValidationUtils.validateRequired(title, "Service Title");
    }
}
