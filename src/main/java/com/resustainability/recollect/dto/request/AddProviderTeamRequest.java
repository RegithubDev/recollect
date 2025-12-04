package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddProviderTeamRequest(
        String teamName
) implements RequestBodyValidator {

    @Override
    public void validate() {

      
        ValidationUtils.validateRequired(teamName, "Team Name");

        ValidationUtils.validateName(teamName);
    }
}
