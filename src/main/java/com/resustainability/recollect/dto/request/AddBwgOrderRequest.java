package com.resustainability.recollect.dto.request;

import java.time.LocalDate;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddBwgOrderRequest(
        Long clientId,
        LocalDate scheduleDate,
        String preferredPaymentMethod,
        String comment
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(clientId);
        
    }
}
