package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdateAssignedVehicleRequest(
        Long id,
        Long vehicleId,
        Long providerId
   
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateId(vehicleId);
        ValidationUtils.validateId(providerId);
    }
}
 