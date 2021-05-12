package com.example.mid_term_assignment_air_quality.controller;

import com.example.mid_term_assignment_air_quality.entities.AirQuality;
import com.example.mid_term_assignment_air_quality.services.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import java.lang.Math;

@RestController
@RequestMapping("/api/airquality")
public class AirQualityRestController {


    @Autowired
    private AirQualityService airQualityService;

    @GetMapping("/cached")
    public Map<String, AirQuality> getCache() {
        return airQualityService.getCache();
    }

    @GetMapping("/statistics")
    public Map<String, String> getStatistics() {
        return airQualityService.getStatistics();
    }


    @GetMapping("/coordinates/{latitude},{longitude}")
    public AirQuality getCityAirQuality(@PathVariable(value = "latitude") double latitude,@PathVariable(value = "longitude") double longitude) {
        return airQualityService.getCoordinatesAirQuality(latitude,longitude);
    }

    @GetMapping("city/{city}")
    public AirQuality getCityAirQuality(@PathVariable(value = "city") String city) {
        return airQualityService.getCityAirQuality(city);
    }

}
