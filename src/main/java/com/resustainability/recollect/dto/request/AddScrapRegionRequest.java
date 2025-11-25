package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.dto.pair.LocalDateIntegerPair;
import com.resustainability.recollect.exception.InvalidDataException;

import java.util.List;

public record AddScrapRegionRequest(
        String name,
        Long districtId,
        String currentWeekDay,
        String nextWeekDay,
        List<LocalDateIntegerPair> availability
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateName(name);
        ValidationUtils.validateDistrictId(districtId);
        if (CollectionUtils.isBlank(availability)) {
            throw new InvalidDataException("Provide availability pickup dates");
        }
    }
}
