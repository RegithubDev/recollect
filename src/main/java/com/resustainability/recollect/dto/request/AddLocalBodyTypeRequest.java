package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddLocalBodyTypeRequest(
        String name
) implements RequestBodyValidator {

    @Override
    public void validate() {
        if (StringUtils.isNotBlank(name)) {
            ValidationUtils.validateName(name);
        }
    }
}
