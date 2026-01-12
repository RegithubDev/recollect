package com.resustainability.recollect.dto.request;

import java.util.List;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateScrapTypeRequest(     
        Long id,
        String scrapName,
        Boolean isPayable,
        Boolean isKg,
        Boolean isActive,       
        List<UpdateScrapPriceRequest> districtPrices

) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateName(scrapName);

        if (districtPrices != null && !districtPrices.isEmpty()) {
        	districtPrices.forEach(UpdateScrapPriceRequest::validate);
        }
    }
}
