package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddPickupVehicleDistrictRequest(
        Long districtId,
        Long vehicleId
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(districtId);
        ValidationUtils.validateId(vehicleId);
    }
}
