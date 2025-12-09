package com.resustainability.recollect.dto.response;

public interface IProviderPickupWeightLimitResponse {

    Long getId();
    Double getWeightLimit();

    Long getProviderId();
    String getProviderName();
}
