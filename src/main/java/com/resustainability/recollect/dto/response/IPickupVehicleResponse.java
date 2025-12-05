package com.resustainability.recollect.dto.response;

public interface IPickupVehicleResponse {

    Long getId();
    String getVehicleName();
    String getVehicleNumber();
    String getPickupType();
    String getVehicleImage();

    Boolean getIsAssigned();
    Boolean getIsActive();
    Boolean getIsDeleted();

    Long getStateId();
    String getStateName();
}
