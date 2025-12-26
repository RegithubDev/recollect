package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.dto.payload.PayloadSelectedItem;
import com.resustainability.recollect.exception.InvalidDataException;

import java.time.LocalDate;
import java.util.List;

public record PlaceOrderRequest(
        LocalDate scheduleDate,
        String altNumber,
        String platform,
        Long addressId,
        List<PayloadSelectedItem> items
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateScheduleDate(scheduleDate);

        if (StringUtils.isNotBlank(altNumber)) {
            ValidationUtils.validateAltPhone(altNumber);
        }

        ValidationUtils.validatePlatform(platform);
        ValidationUtils.validateAddressId(addressId);

        final boolean noCategorySelected = CollectionUtils.isBlank(items)
                || items.stream().noneMatch(item -> item.id() != null);
        if (noCategorySelected) {
            throw new InvalidDataException("Order cannot be placed without selecting a category item.");
        }
    }
}
