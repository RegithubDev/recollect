package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddProviderTeamAllotedWardRequest(

        Long teamId,
        Long wardId,

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

        ValidationUtils.validateId(teamId);
        ValidationUtils.validateId(wardId);

        ValidationUtils.validateRequired(monday, "Monday");
        ValidationUtils.validateRequired(tuesday, "Tuesday");
        ValidationUtils.validateRequired(wednesday, "Wednesday");
        ValidationUtils.validateRequired(thursday, "Thursday");
        ValidationUtils.validateRequired(friday, "Friday");
        ValidationUtils.validateRequired(saturday, "Saturday");
        ValidationUtils.validateRequired(sunday, "Sunday");
    }
}
