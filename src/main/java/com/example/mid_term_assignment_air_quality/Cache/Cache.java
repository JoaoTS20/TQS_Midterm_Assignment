package com.example.mid_term_assignment_air_quality.Cache;

import com.example.mid_term_assignment_air_quality.entities.AirQuality;
import com.example.mid_term_assignment_air_quality.entities.AirQualityData;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cache {
    Logger logger = Logger.getLogger(Cache.class.getName());

    // Data from Cache
    private static Map<String, AirQuality> cache_memory = new HashMap<>();

    //Content was successfully served from the cache.
    private static int hit = 0;

    //Memory was searched but the data isn’t found.
    private static int miss = 0;


    public Cache() {
        //Empty Constructor to initiate Cache
    }

    // Returns some basic statistics about its operation (count of requests, hits/misses) and Cities in the Memory
    public Map<String,String> getStatistics() {
        this.setHit();
        Map<String, String> statistics = new HashMap<>();
        statistics.put("miss", String.valueOf(miss));
        statistics.put("hit", String.valueOf(hit));
        statistics.put("citiesAirInfoInCache", cache_memory.keySet().toString());
        return statistics;
    }

    public synchronized void  setMiss() { miss++; }

    public synchronized void setHit() {
        hit++;
    }

    //Validates that the City is in the memory and that it respects the time-to-live policy
    // ,in other words, that its life time has not passed 10 minutes (600 000 milliseconds)
    public boolean isValidCity(String city) {
        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        if (cache_memory.containsKey(city)){
            logger.log(Level.INFO, "CACHE MEMORY CONTAINS CITY");
            if(currentTime - cache_memory.get(city).getTimestamp() < 600000){
                logger.log(Level.INFO, "TIME-TO-LIVE POLICY RESPECTED");
            }
            else{
                logger.log(Level.INFO, "FAILED TIME-TO-LIVE POLICY");
            }
        }
        else{
            logger.log(Level.INFO, "CACHE MEMORY DOES NOT CONTAIN CITY");
        }
        return (cache_memory.containsKey(city) && currentTime - cache_memory.get(city).getTimestamp() < 600000);
    }

    public boolean isValidCoordinates(String lat, String lon) {
        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        for (AirQuality x : cache_memory.values()) {
            if (x.getLat().equals(lat) && x.getLon().equals(lon)) {
                {
                    logger.log(Level.INFO, "CACHE MEMORY CONTAINS COORDINATES ");
                    if (currentTime - cache_memory.get(x.getCity_name().toLowerCase()).getTimestamp() < 600000) {
                        logger.log(Level.INFO, "TIME-TO-LIVE POLICY RESPECTED");
                    } else {
                        logger.log(Level.INFO, "FAILED TIME-TO-LIVE POLICY");
                    }
                    return (cache_memory.containsKey(x.getCity_name().toLowerCase()) && currentTime - cache_memory.get(x.getCity_name().toLowerCase()).getTimestamp() < 600000);
                }
            }
        }
        logger.log(Level.INFO, "CACHE MEMORY DOES NOT CONTAIN COORDINATES");
        return false;
    }

    //Save City and respective Air Quality in Cache Memory
    public void saveCityAirQuality(String city, AirQuality location) {
        cache_memory.put(city.toLowerCase(), location);
    }

    //Get City Air Quality Data
    public AirQuality getCityAirQuality(String city) {
        return cache_memory.get(city.toLowerCase());
    }

    //Get Coordinates Air Quality Data
    public AirQuality getCoordinatesAirQuality(String lat, String lon) {
        for(AirQuality x : cache_memory.values()){
            if(x.getLat().equals(lat) && x.getLon().equals(lon)){
                return  cache_memory.get(x.getCity_name().toLowerCase());
            }
        }
        return new AirQuality(0,"error","","", new AirQualityData[]{});
    }


    //Returns cities and correspondent Air Quality in cache the Cache Memory
    public Map<String, AirQuality> getCacheMemory() {
        return cache_memory;
    }

    public synchronized void reset(){
        getCacheMemory().clear();
        hit=0;
        miss=0;
    }


}
