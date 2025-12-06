package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

import java.math.BigDecimal;

public record UpdateWasteBagRequest(
        Long id,
        String bagSize,
        BigDecimal bagPrice,
        Double bagCgst,
        Double bagSgst,
        Long stateId,
        Boolean isActive,
        Boolean isBwg
) implements RequestBodyValidator {

	@Override
	public void validate() {
	    ValidationUtils.validateId(id);                        
	    ValidationUtils.validateRequired(bagSize, "Bag Size");
	    ValidationUtils.validateBigDecimal(bagPrice, "Bag Price");
	    ValidationUtils.validateNonNegative(bagCgst, "CGST");
	    ValidationUtils.validateNonNegative(bagSgst, "SGST");
	    ValidationUtils.validateId(stateId);
	    ValidationUtils.validateRequired(isActive, "Is Active");
	    ValidationUtils.validateRequired(isBwg, "Is BWG");
	}

}
