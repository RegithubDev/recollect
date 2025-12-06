package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

import java.math.BigDecimal;

public record AddWasteBagRequest(
        String bagSize,
        BigDecimal bagPrice,
        Double bagCgst,
        Double bagSgst,
        Long stateId,
        Boolean isBwg
) implements RequestBodyValidator {

	@Override
	public void validate() {
	    ValidationUtils.validateRequired(bagSize, "Bag Size");
	    ValidationUtils.validateBigDecimal(bagPrice, "Bag Price");          
	    ValidationUtils.validateNonNegative(bagCgst, "CGST");
	    ValidationUtils.validateNonNegative(bagSgst, "SGST");
	    ValidationUtils.validateId(stateId);                  
	    ValidationUtils.validateRequired(isBwg, "Is BWG");
	}
}
