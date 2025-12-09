package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdatePickupVehicleDistrictRequest(
        Long id,
        Long districtId,
        Long vehicleId,
        Boolean isActive
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateId(districtId);
        ValidationUtils.validateId(vehicleId);
        ValidationUtils.validateRequired(isActive, "Is Active");
    }
}
