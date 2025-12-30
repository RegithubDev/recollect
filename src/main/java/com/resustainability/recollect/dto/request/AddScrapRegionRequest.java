package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;
import com.resustainability.recollect.dto.payload.PayloadScrapRegionAvailability;

import org.locationtech.jts.geom.Geometry;

import java.util.List;

public record AddScrapRegionRequest(
        String name,
        Long districtId,
        String currentWeekDay,
        String nextWeekDay,
        Geometry geometry,
        List<PayloadScrapRegionAvailability> availability
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateName(name);
        ValidationUtils.validateDistrictId(districtId);
        ValidationUtils.validateScrapRegionAvailability(availability);
    }
}
