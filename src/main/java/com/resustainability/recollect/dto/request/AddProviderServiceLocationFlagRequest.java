package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddProviderServiceLocationFlagRequest(
        Long providerId,
        Boolean allScrapDistrict,
        Boolean allBioDistrict,
        Boolean allScrapRegions,
        Boolean allLocalBodies,
        Boolean allWards
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateId(providerId);
        ValidationUtils.validateRequired(allScrapDistrict, "All Scrap District");
        ValidationUtils.validateRequired(allBioDistrict, "All Bio District");
        ValidationUtils.validateRequired(allScrapRegions, "All Scrap Regions");
        ValidationUtils.validateRequired(allLocalBodies, "All Local Bodies");
        ValidationUtils.validateRequired(allWards, "All Wards");
    }
}
