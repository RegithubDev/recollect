package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddBwgOrderCartRequest(
        Long orderId,
        Long scrapTypeId,
        Long bioWasteTypeId,
        Double scrapWeight,
        Double scrapPrice,
        Double scrapGst,
        String scrapHsn
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateOrderId(orderId);

        if (null == scrapTypeId) {
            ValidationUtils.validateId(bioWasteTypeId);
        } else {
            ValidationUtils.validateId(scrapTypeId);
        }

        ValidationUtils.validateNonNegative(scrapWeight, "Scrap Weight");
        ValidationUtils.validateNonNegative(scrapPrice, "Scrap Price");
        ValidationUtils.validateNonNegative(scrapGst, "Scrap GST");
    }
}
