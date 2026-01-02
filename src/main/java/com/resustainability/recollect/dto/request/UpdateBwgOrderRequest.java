package com.resustainability.recollect.dto.request;

import java.time.LocalDate;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateBwgOrderRequest(
        Long id,
        String comment,
        LocalDate scheduleDate,
        String preferredPaymentMethod,
        String orderStatus
               
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
    }
}
