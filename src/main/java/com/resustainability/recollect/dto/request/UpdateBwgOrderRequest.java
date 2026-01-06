package com.resustainability.recollect.dto.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateBwgOrderRequest(
        Long id,
        String comment,
        LocalDate scheduleDate,
        String preferredPaymentMethod,
        String orderStatus,
        List<UpdateBwgOrderCartRequest> types,
        List<UpdateBwgOrderUsedBagRequest> usedBags
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        if (CollectionUtils.isNonBlank(types)) {
            types.stream().filter(Objects::nonNull).forEach(UpdateBwgOrderCartRequest::validate);
        }
        if (CollectionUtils.isNonBlank(usedBags)) {
            usedBags.stream().filter(Objects::nonNull).forEach(UpdateBwgOrderUsedBagRequest::validate);
        }
    }
}
