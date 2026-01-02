package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.dto.payload.PayloadLocalBodyAvailability;

import org.locationtech.jts.geom.Geometry;

import java.util.List;

public record AddLocalBodyRequest(
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
        Geometry geometry,
        List<PayloadLocalBodyAvailability> availability
) implements RequestBodyValidator {

    @Override
    public void validate() {
        if (StringUtils.isNotBlank(name)) {
            ValidationUtils.validateName(name);
        }

        ValidationUtils.validateId(districtId);
        ValidationUtils.validateId(localBodyTypeId);
        ValidationUtils.validateLocalBodyAvailability(availability);
    }
}
