package com.resustainability.recollect.dto.response;

import org.locationtech.jts.geom.Polygon;

public interface IGeometryResponse {
    Long getId();
    Polygon getGeometry();
}
