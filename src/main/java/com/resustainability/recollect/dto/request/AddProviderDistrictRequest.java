package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddProviderDistrictRequest(
        Long providerId,
        Long districtId,
        Boolean scrapAllowed,
        Boolean bioAllowed,
        Boolean isActive
) implements RequestBodyValidator {

	@Override
	public void validate() {
	    ValidationUtils.validateRequired(scrapAllowed, "Scrap Allowed");
	    ValidationUtils.validateRequired(bioAllowed, "Bio Allowed");
	    ValidationUtils.validateRequired(isActive, "Is Active");

	    ValidationUtils.validateId(districtId);
	    ValidationUtils.validateId(providerId);
	}
}
