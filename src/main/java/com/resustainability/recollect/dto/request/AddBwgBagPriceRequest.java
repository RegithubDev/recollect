package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

public record AddBwgBagPriceRequest(
        String bagSize,
        Double bagPrice,
        Double bagCgst,
        Double bagSgst,
        Long clientId
) implements RequestBodyValidator {

    @Override
    public void validate() {
        ValidationUtils.validateRequired(bagSize, "Bag Size");
        ValidationUtils.validateNonNegative(bagPrice, "Bag Price");
        ValidationUtils.validateNonNegative(bagCgst, "CGST");
        ValidationUtils.validateNonNegative(bagSgst, "SGST");
        ValidationUtils.validateId(clientId);
    }
}
