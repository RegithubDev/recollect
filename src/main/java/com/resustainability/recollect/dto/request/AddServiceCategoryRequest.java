package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddServiceCategoryRequest(
        String name,
        String title,
        String subtitle,
        String categoryUrl,
        String orderUrl,
        String scheduleUrl
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateName(name);
        ValidationUtils.validateRequired(title, "Service Title");
    }
}
