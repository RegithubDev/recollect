package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateWardRequest(
        Long id,
        Integer wardNo,
        String name,
        String currentWeekDay,
        String nextWeekDay,
        Long localBodyId,
        Boolean isActive
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateId(localBodyId);
        ValidationUtils.validateNonNegative(wardNo, "Ward number");
        if (StringUtils.isNotBlank(name)) {
            ValidationUtils.validateName(name);
        }
    }
}
