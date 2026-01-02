package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateBwgOrderCartRequest(
        Long id,
        Double scrapWeight,
        Double scrapPrice,
        Double scrapGst,
        String scrapHsn
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateNonNegative(scrapWeight, "Scrap Weight");
        ValidationUtils.validateNonNegative(scrapPrice, "Scrap Price");
        ValidationUtils.validateNonNegative(scrapGst, "Scrap GST");
    }
}
