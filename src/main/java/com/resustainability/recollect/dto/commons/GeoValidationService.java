package com.resustainability.recollect.dto.commons;

import java.util.List;

import org.springframework.stereotype.Component;

import com.resustainability.recollect.exception.ResourceNotFoundException;
@Component
public class GeoValidationService {

	public static void validatePointInsidePolygon(
            double lat,
            double lon,
            String polygonText,
            String errorMessage) {

        List<GeoPoint> polygon = PolygonUtil.parsePolygon(polygonText);

        boolean inside = GeoFenceUtil.isPointInsidePolygon(lat, lon, polygon);

        if (!inside) {
            throw new ResourceNotFoundException(errorMessage);
        }
    }
}