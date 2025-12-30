package com.resustainability.recollect.dto.response;

import org.locationtech.jts.geom.MultiPolygon;

public interface IGeometryResponse {
    Long getId();
    MultiPolygon getGeometry();
}
