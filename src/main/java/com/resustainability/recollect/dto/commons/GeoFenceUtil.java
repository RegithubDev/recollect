package com.resustainability.recollect.dto.commons;

import java.util.List;

public class GeoFenceUtil {

    public static boolean isPointInsidePolygon(
            double lat,
            double lon,
            List<GeoPoint> polygon) {

        int n = polygon.size();
        boolean inside = false;

        // X = longitude, Y = latitude
        double x = lon;
        double y = lat;

        for (int i = 0, j = n - 1; i < n; j = i++) {

            double xi = polygon.get(i).getLon();
            double yi = polygon.get(i).getLat();
            double xj = polygon.get(j).getLon();
            double yj = polygon.get(j).getLat();

            // Check if point is exactly on polygon edge
            if (isPointOnSegment(x, y, xi, yi, xj, yj)) {
                return true;
            }

            boolean intersect =
                    ((yi > y) != (yj > y)) &&
                    (x < (xj - xi) * (y - yi) / (yj - yi) + xi);

            if (intersect) {
                inside = !inside;
            }
        }

        return inside;
    }

    private static boolean isPointOnSegment(
            double px, double py,
            double x1, double y1,
            double x2, double y2) {

        double cross =
                (px - x1) * (y2 - y1) -
                (py - y1) * (x2 - x1);

        if (Math.abs(cross) > 1e-8) {
            return false;
        }

        double dot =
                (px - x1) * (px - x2) +
                (py - y1) * (py - y2);

        return dot <= 0;
    }
}
