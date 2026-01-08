package com.resustainability.recollect.dto.request;

import java.time.LocalDate;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateOrderScheduleDateRequest(
	
    Long id,
    LocalDate scheduleDate
    
) implements RequestBodyValidator {
	
@Override
public void validate() {
    ValidationUtils.validateId(id);
    ValidationUtils.validateScheduleDate(scheduleDate);
}

}
