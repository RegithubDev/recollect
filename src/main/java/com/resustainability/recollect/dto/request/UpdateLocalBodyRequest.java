package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateLocalBodyRequest(
        Long id,
        String name,
        Long districtId,
        Long localBodyTypeId,
        String borderPolygon,
        Double bioProcessingCharge,
        Double bioServiceCharge,
        Double bioSubsidyAmount,
        Double bioCgstPercentage,
        Double bioSgstPercentage,
        Double bioResidentialPrice,
        Double bioCommercialPrice,
        Boolean isInclusiveCommercial,
        Boolean isInclusiveResidential,
        Boolean isActive
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);

        if (StringUtils.isNotBlank(name)) {
            ValidationUtils.validateName(name);
        }

        ValidationUtils.validateId(districtId);
        ValidationUtils.validateId(localBodyTypeId);
    }
}
