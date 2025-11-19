package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateCountryRequest(
        Long id,
        String code,
        String name,
        Boolean isActive
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateCode(code);
        if (StringUtils.isNotBlank(name)) {
            ValidationUtils.validateName(name);
        }
    }
}
