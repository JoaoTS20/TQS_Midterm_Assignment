package com.example.mid_term_assignment_air_quality.services;

import com.example.mid_term_assignment_air_quality.Cache.Cache;
import com.example.mid_term_assignment_air_quality.entities.AirQuality;
import com.example.mid_term_assignment_air_quality.entities.AirQualityData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AirQualityService {
    Logger logger = Logger.getLogger(AirQualityService.class.getName());
    Cache cache = new Cache();
    private static final String URL = "http://api.weatherbit.io/v2.0/current/airquality?";
    private static final String KEY = "&key=0d3b6b627f344f13aa9e37ba3aa538ce";


    public Map<String, AirQuality> getCache(){
        logger.log(Level.INFO, "GET CITIES AIR QUALITY IN CACHE");
        return cache.getCacheMemory();
    }


    // Get basic statistics about its operation (count of requests, hits/misses) and Cities in the Cache
    public Map<String, String> getStatistics() {
        logger.log(Level.INFO, "GET CACHE STATISTICS");
        return cache.getStatistics();
    }


    //Save City and Air Quality in the Cache
    public void saveAirQuality(String city, AirQuality airQuality) {
        logger.log(Level.INFO, "CITY AND AIRQUALITY TO CACHE");
        cache.saveCityAirQuality(city, airQuality);
    }

    //Get Air Quality from a City
    public AirQuality getCityAirQuality(String city) {
        //If not present in the Cache or not respecting the  time-to-live policy
        logger.log(Level.INFO, "CHECKING CACHE");
        if (!cache.isValidCity(city.toLowerCase())) {
            logger.log(Level.INFO, "ACCESSING EXTERNAL SOURCE");

            RestTemplate restTemplate = new RestTemplate();

            //Making Complete URL to access External API
            String completeUrl = URL + "city=" + city + KEY;

            logger.log(Level.INFO, "URL:" + completeUrl);

            //Getting data
            AirQuality location_airQuality = restTemplate.getForObject(completeUrl, AirQuality.class);

            //Verifying return data
            if (location_airQuality != null) {
                logger.log(Level.WARNING, "VALID CITY");
                cache.setMiss();
                location_airQuality.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
                this.saveAirQuality(city, location_airQuality);
                return cache.getCityAirQuality(city);
            }
            else
                // If not present in the external source
                logger.log(Level.WARNING, "INVALID CITY");
                logger.log(Level.INFO, "RETURNING NULL OBJECT");
                return new AirQuality(0,"error","","", new AirQualityData[]{});
        }
        //If present in the Cache AND respecting the time-to-live policy
        else {
            logger.log(Level.INFO, "ACCESSING CACHE MEMORY");
            cache.setHit();
            return cache.getCityAirQuality(city);
        }
    }

    //Get Air Quality from a City
    public AirQuality getCoordinatesAirQuality(double lat, double lon) {
        //If not present in the Cache or not respecting the  time-to-live policy
        logger.log(Level.INFO, "CHECKING CACHE");
        String lat_string=String.valueOf(Math.round(lat*100.0)/100.0);
        String lon_string=String.valueOf(Math.round(lon*100.0)/100.0);

        if (!cache.isValidCordinates(lat_string, lon_string)) {
            logger.log(Level.INFO, "ACCESSING EXTERNAL SOURCE");

            RestTemplate restTemplate = new RestTemplate();

            //Making Complete URL to access External API
            String completeUrl = URL + "lat="+lat_string +"&lon="+ lon_string + KEY;

            logger.log(Level.INFO, "URL:" + completeUrl);

            //Getting data
            AirQuality location_airQuality = restTemplate.getForObject(completeUrl, AirQuality.class);

            //Verifying return data
            if (location_airQuality != null) {
                logger.log(Level.WARNING, "VALID COORDINATES");
                cache.setMiss();
                location_airQuality.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
                this.saveAirQuality(location_airQuality.getCity_name(), location_airQuality);
                return cache.getCityAirQuality(location_airQuality.getCity_name());
            }
            else
                // If not present in the external source
                logger.log(Level.WARNING, "INVALID COORDINATES");
                logger.log(Level.INFO, "RETURNING NULL OBJECT");
                return new AirQuality(0,"error","","", new AirQualityData[]{});
        }
        //If present in the Cache AND respecting the time-to-live policy
        else {
            logger.log(Level.INFO, "ACCESSING CACHE MEMORY");
            cache.setHit();
            return cache.getCoordinatesAirQuality(lat_string,lon_string);
        }
    }
}
