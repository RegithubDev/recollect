package com.resustainability.recollect.dto.response;

public interface IProviderServiceLocationFlagResponse {

    Long getId();

    Long getProviderId();
    String getProviderName();

    Boolean getAllScrapDistrict();
    Boolean getAllBioDistrict();
    Boolean getAllScrapRegions();
    Boolean getAllLocalBodies();
    Boolean getAllWards();
}
