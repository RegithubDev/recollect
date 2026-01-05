package com.resustainability.recollect.util;

import com.resustainability.recollect.commons.CollectionUtils;
import com.resustainability.recollect.exception.InvalidDataException;

import org.locationtech.jts.geom.*;

import org.locationtech.jts.operation.union.UnaryUnionOp;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GeometryNormalizer {

    public static final int SRID = 4326;

    private static final Pattern COORD_PATTERN =
            Pattern.compile("\\((-?\\d+\\.\\d+),\\s*(-?\\d+\\.\\d+)\\)");

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

    /**
     * Parses "(lat, lon),(lat, lon),..." into MultiPolygon
     */
    public MultiPolygon parseToMultiPolygon(String input) {

        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input string is empty");
        }

        List<Coordinate> coordinates = new ArrayList<>();
        Matcher matcher = COORD_PATTERN.matcher(input);

        while (matcher.find()) {
            double lat = Double.parseDouble(matcher.group(1));
            double lon = Double.parseDouble(matcher.group(2));

            // JTS order: (lon, lat)
            coordinates.add(new Coordinate(lon, lat));
        }

        if (coordinates.size() < 4) {
            throw new IllegalArgumentException("Polygon requires at least 4 points");
        }

        // Close ring
        if (!coordinates.get(0).equals2D(coordinates.get(coordinates.size() - 1))) {
            coordinates.add(new Coordinate(coordinates.get(0)));
        }

        LinearRing shell = geometryFactory.createLinearRing(
                coordinates.toArray(Coordinate[]::new)
        );

        Polygon polygon = geometryFactory.createPolygon(shell);
        polygon.setSRID(SRID);

        Geometry fixed = polygon;

        if (!polygon.isValid()) {
            fixed = polygon.buffer(0);
            fixed.setSRID(SRID);
        }

        return toMultiPolygon(fixed);
    }

    /**
     * Merge multiple MultiPolygons into a single MultiPolygon
     */
    public MultiPolygon merge(Collection<MultiPolygon> multiPolygons) {

        if (CollectionUtils.isBlank(multiPolygons)) {
            throw new IllegalArgumentException("No geometries provided for merge");
        }

        // Filter nulls & fix SRID
        List<Geometry> geometries = multiPolygons.stream()
                .filter(Objects::nonNull)
                .map(g -> {
                    g.setSRID(SRID);
                    return g.isValid() ? (Geometry) g : g.buffer(0);
                })
                .toList();

        final Geometry unioned = UnaryUnionOp.union(geometries);
        unioned.setSRID(SRID);

        // Ensure MultiPolygon return
        if (unioned instanceof MultiPolygon mp) {
            return mp;
        }

        if (unioned instanceof Polygon p) {
            return geometryFactory.createMultiPolygon(new Polygon[]{p});
        }

        throw new IllegalStateException(
                "Union result is not Polygon/MultiPolygon: " +
                        unioned.getGeometryType()
        );
    }
}
