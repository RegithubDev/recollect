package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateProviderTeamRequest(
        Long id,
        String teamName,
        Boolean isActive
) implements RequestBodyValidator {

    @Override
    public void validate() {

        ValidationUtils.validateId(id);

        if (StringUtils.isNotBlank(teamName)) {
            ValidationUtils.validateName(teamName);
        }
    }
}
