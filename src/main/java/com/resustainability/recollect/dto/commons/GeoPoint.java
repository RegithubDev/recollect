package com.resustainability.recollect.dto.commons;

public class GeoPoint {
	 private final double lat;
	    private final double lon;

	    public GeoPoint(double lat, double lon) {
	        this.lat = lat;
	        this.lon = lon;
	    }

	    public double getLat() { return lat; }
	    public double getLon() { return lon; }
}
