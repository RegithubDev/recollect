package com.resustainability.recollect.dto.request;

import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.commons.RequestBodyValidator;

import org.locationtech.jts.geom.Geometry;

public record UpdateScrapRegionBorderRequest(
        Long id,
        Geometry geometry
) implements RequestBodyValidator {
    @Override
    public void validate() {
        ValidationUtils.validateId(id);
        ValidationUtils.validateGeometry(geometry);
    }
}
