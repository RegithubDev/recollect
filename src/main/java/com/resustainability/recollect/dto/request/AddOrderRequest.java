package com.resustainability.recollect.dto.request;

import java.time.LocalDate;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddOrderRequest(
        String phoneNumber,
        Long customerId,
        String customerAddressId,
        LocalDate scheduleDate,
        String preferredPaymentMethod,
        String comment
      //  String scrapTypeId
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(customerId);

        ValidationUtils.validateRequired(phoneNumber, "Phone number");
        ValidationUtils.validatePhone(phoneNumber);

        ValidationUtils.validateRequired(customerAddressId, "Customer address");
        ValidationUtils.validateScheduleDate(scheduleDate);

        ValidationUtils.validateRequired(preferredPaymentMethod, "Preferred payment method");

        
       // ValidationUtils.validateRequired(scrapTypeId, "Scrap type");
    }
}
