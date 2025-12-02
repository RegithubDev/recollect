package com.resustainability.recollect.dto.response;

public interface IProviderScrapRegionResponse {

    Long getId();
    Boolean getIsActive();

    Long getProviderId();
    String getProviderName();

    Long getScrapRegionId();
    String getScrapRegionName();
}
