package com.resustainability.recollect.util;

import com.resustainability.recollect.exception.InvalidDataException;
import org.locationtech.jts.geom.*;

import org.springframework.stereotype.Component;

@Component
public class GeometryNormalizer {

    public static final int SRID = 4326;

    private final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), SRID);

    /**
     * Accepts Polygon or MultiPolygon and always returns MultiPolygon.
     */
    public MultiPolygon toMultiPolygon(Geometry geometry) {
        if (geometry == null) {
            throw new IllegalArgumentException("Geometry must not be null");
        }

        geometry.setSRID(SRID);

        if (geometry instanceof MultiPolygon mp) {
            return mp;
        }

        if (geometry instanceof Polygon p) {
            return geometryFactory.createMultiPolygon(new Polygon[]{p});
        }

        throw new InvalidDataException(
                "Unsupported geometry type: " + geometry.getGeometryType()
        );
    }
}
