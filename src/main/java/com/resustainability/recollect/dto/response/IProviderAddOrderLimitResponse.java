package com.resustainability.recollect.dto.response;

public interface IProviderAddOrderLimitResponse {

    Long getId();
    Integer getMaxLimit();
    Integer getCurrentLimit();
    Long getProviderId();
}
