package com.example.punyaaachman.albus.POJO;

/**
 * Created by samarthgupta on 27/05/17.
 */

public class CoordinatesInfo {
    double lat;
    double lon;

    public CoordinatesInfo() {

    }

    public CoordinatesInfo(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
