package com.example.mid_term_assignment_air_quality.entities;

import java.util.Arrays;

public class AirQuality {
    private long timestamp;
    private String city_name;
    private String lat;
    private String lon;
    private AirQualityData[] data;

    public AirQuality() {

    }

    public AirQuality(long timestamp, String city_name, String lat, String lon, AirQualityData[] data) {
        this.timestamp = timestamp;
        this.city_name = city_name;
        this.lat = lat;
        this.lon = lon;
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public AirQualityData[] getData() {
        return data;
    }

    public void setData(AirQualityData[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "timestamp=" + timestamp +
                ", city_name='" + city_name + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
