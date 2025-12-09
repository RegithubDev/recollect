package com.resustainability.recollect.dto.response;

public interface IPickupVehicleDistrictResponse {
    Long getId();
    Boolean getIsActive();

    Long getDistrictId();
    String getDistrictName();

    Long getVehicleId();
    String getVehicleName();
    String getVehicleNumber();
}
