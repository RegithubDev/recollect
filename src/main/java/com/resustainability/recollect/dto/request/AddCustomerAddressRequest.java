package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddCustomerAddressRequest(
        Long customerId,
        Long scrapRegionId,
        Long wardId,
        Boolean isScrapLocationActive,
        Boolean isBioWasteLocationActive,
        String residenceType,
        String residenceDetails,
        String landmark,
        String latitude,
        String longitude
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateLatitude(latitude);
        ValidationUtils.validateLongitude(longitude);

        if (StringUtils.isNotBlank(residenceType)) {
            ValidationUtils.validateResidenceType(residenceType);
        }

        if (StringUtils.isNotBlank(residenceDetails)) {
            ValidationUtils.validateResidenceDetails(residenceDetails);
        }

        if (StringUtils.isNotBlank(landmark)) {
            ValidationUtils.validateLandmark(landmark);
        }
    }
}
