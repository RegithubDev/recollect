package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateWardRequest(
        Long id,
        Integer wardNo,
        String wardName,
        String wardWeekdayCurrent,
        String wardWeekdayNext,
        Long localbodyId,
        Boolean isActive
) implements RequestBodyValidator {

    @Override
    public void validate() {

        ValidationUtils.validateId(id);
        ValidationUtils.validateId(localbodyId);

        if (wardNo == null || wardNo <= 0) {
            throw new IllegalArgumentException("Ward number must be positive");
        }

        if (StringUtils.isNotBlank(wardName)) {
            ValidationUtils.validateName(wardName);
        }
    }
}
