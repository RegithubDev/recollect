package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record UpdatePickupVehicleRequest(
        Long id,
        String vehicleName,
        String vehicleNumber,
        String pickupType,
        Long stateId,
        Boolean isActive,
        Boolean isAssigned
) implements RequestBodyValidator {

    @Override
    public void validate() {

        ValidationUtils.validateId(id);

        ValidationUtils.validateRequired(vehicleName, "Vehicle Name");
        ValidationUtils.validateLength(vehicleName, 2, 100, "Vehicle Name");

        ValidationUtils.validateRequired(vehicleNumber, "Vehicle Number");
        ValidationUtils.validateLength(vehicleNumber, 2, 20, "Vehicle Number");

        ValidationUtils.validateRequired(pickupType, "Pickup Type");
        ValidationUtils.validateLength(pickupType, 2, 20, "Pickup Type");

        ValidationUtils.validateId(stateId);
    }
}
