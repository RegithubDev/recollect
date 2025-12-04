package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateProviderTeamAllotedWardRequest(

        Long id,

        Boolean monday,
        Boolean tuesday,
        Boolean wednesday,
        Boolean thursday,
        Boolean friday,
        Boolean saturday,
        Boolean sunday

) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
    }
}
