package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.dto.payload.PayloadLocalBodyAvailability;

import java.util.List;

public record UpdateLocalBodyRequest(
        Long id,
        String name,
        Long districtId,
        Long localBodyTypeId,
        Double bioProcessingCharge,
        Double bioServiceCharge,
        Double bioSubsidyAmount,
        Double bioCgstPercentage,
        Double bioSgstPercentage,
        Double bioResidentialPrice,
        Double bioCommercialPrice,
        Boolean isInclusiveCommercial,
        Boolean isInclusiveResidential,
        Boolean isActive,
        List<PayloadLocalBodyAvailability> availability
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);

        if (StringUtils.isNotBlank(name)) {
            ValidationUtils.validateName(name);
        }

        ValidationUtils.validateId(districtId);
        ValidationUtils.validateId(localBodyTypeId);
        ValidationUtils.validateLocalBodyAvailability(availability);
    }
}
