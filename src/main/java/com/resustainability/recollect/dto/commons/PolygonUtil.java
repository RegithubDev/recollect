package com.resustainability.recollect.dto.commons;

import java.util.ArrayList;
import java.util.List;

public class PolygonUtil {
	 public static List<GeoPoint> parsePolygon(String polygonText) {
	        List<GeoPoint> points = new ArrayList<>();

	        String cleaned = polygonText
	                .replace("(", "")
	                .replace(")", "");

	        String[] pairs = cleaned.split(",");

	        for (int i = 0; i < pairs.length; i += 2) {
	            double lat = Double.parseDouble(pairs[i].trim());
	            double lon = Double.parseDouble(pairs[i + 1].trim());
	            points.add(new GeoPoint(lat, lon));
	        }

	        return points;
	    }
	 
	
}
