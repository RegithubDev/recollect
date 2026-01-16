package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.dto.payload.PayloadSelectedItem;
import com.resustainability.recollect.exception.InvalidDataException;

import java.util.List;

public record CaptureItemsWeightRequest(
        List<PayloadSelectedItem> items
) implements RequestBodyValidator {
    @Override
    public void validate() {
        final boolean noCategorySelected = CollectionUtils.isBlank(items)
                || items.stream().noneMatch(item -> item.id() != null && item.weight() != null && item.weight() > 0.0);
        if (noCategorySelected) {
            throw new InvalidDataException("Weights cannot be updated without providing a valid input.");
        }
    }
}
