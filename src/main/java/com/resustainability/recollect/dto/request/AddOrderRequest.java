package com.resustainability.recollect.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddOrderRequest(
        String phoneNumber,
        Long customerId,
        Long customerAddressId,
        LocalDate scheduleDate,
        String preferredPaymentMethod,
        String comment,
        List<Long> typeIds , 
        
        
        Long districtId,
        Long scrapRegionId,       // scrap only
        Long wardId,              // bio only
        String address,
        String landmark,
        String addressType
        
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(customerId);

        ValidationUtils.validateRequired(phoneNumber, "Phone number");
        ValidationUtils.validatePhone(phoneNumber);

       // ValidationUtils.validateRequired(customerAddressId, "Customer address");
        ValidationUtils.validateScheduleDate(scheduleDate);

        ValidationUtils.validateRequired(preferredPaymentMethod, "Preferred payment method");

        if (typeIds == null || typeIds.isEmpty()) {
            throw new IllegalArgumentException("At least one scrap type must be selected");
        }
    }
}
