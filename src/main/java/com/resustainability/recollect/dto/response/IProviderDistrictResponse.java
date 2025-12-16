package com.resustainability.recollect.dto.response;

public interface IProviderDistrictResponse {
    Long getId();

    Boolean getScrapAllowed();
    Boolean getBioAllowed();
    Boolean getIsActive();

    Long getDistrictId();
    String getDistrictName();
    String getDistrictCode();

    Long getProviderId();
}
