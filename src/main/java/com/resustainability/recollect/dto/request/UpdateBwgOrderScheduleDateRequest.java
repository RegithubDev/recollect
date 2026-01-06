package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

import java.time.LocalDate;

public record UpdateBwgOrderScheduleDateRequest(
        Long id,
        LocalDate scheduleDate
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateScheduleDate(scheduleDate);
    }
}
