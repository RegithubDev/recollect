package com.resustainability.recollect.dto.response;

public interface IAssignedVehicleResponse {

    Long getId();

    Long getVehicleId();
    String getVehicleNumber();
    String getVehicleName();

    Long getProviderId();
    String getProviderName();
}
