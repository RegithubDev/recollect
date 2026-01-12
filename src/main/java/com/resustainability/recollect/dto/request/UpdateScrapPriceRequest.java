package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateScrapPriceRequest(
        Long districtId,
        Double scrapPrice,
        Double scrapCgst,
        Double scrapSgst,
        Boolean isActive
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(districtId);
       
    }
}
