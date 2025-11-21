package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddDistrictRequest(
		String code,
		String name,
		Long stateId
)implements RequestBodyValidator {
	
	  @Override
	    public void validate() {
	        ValidationUtils.validateCode(code);
	        if (StringUtils.isNotBlank(name)) {
	            ValidationUtils.validateName(name);
	        }

	        ValidationUtils.validateId(stateId);
	    }

}
