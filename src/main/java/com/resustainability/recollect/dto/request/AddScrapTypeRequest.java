package com.resustainability.recollect.dto.request;

import java.util.List;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddScrapTypeRequest(
        String scrapName,
        Boolean isPayable,
        Boolean isKg,
        Long scrapCategoryId,       
        List<ScrapDistrictPriceRequest> districtPrices
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateName(scrapName);
        ValidationUtils.validateId(scrapCategoryId);

        if (districtPrices != null && !districtPrices.isEmpty()) {
            for (ScrapDistrictPriceRequest price : districtPrices) {
                ValidationUtils.validateId(price.districtId());
                ValidationUtils.validateRequired(price.scrapPrice(), "Scrap price");
            }
        }
    }
}
