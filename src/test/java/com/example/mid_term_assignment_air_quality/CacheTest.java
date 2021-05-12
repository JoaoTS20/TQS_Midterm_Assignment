package com.example.mid_term_assignment_air_quality;

import com.example.mid_term_assignment_air_quality.Cache.Cache;
import com.example.mid_term_assignment_air_quality.entities.AirQuality;
import com.example.mid_term_assignment_air_quality.entities.AirQualityData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CacheTest {

    protected Cache cache;

    @BeforeEach
    public void setUp() {
        cache = new Cache();
    }
    @AfterEach
    public void clearCache(){
        cache.reset();
    }

    @Test
    public void getCityAirQuality_Test() {
        AirQuality aveiro_airquality = new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"Aveiro","40.64427","-8.64554", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)});
        cache.saveCityAirQuality("Aveiro", aveiro_airquality);
        AirQuality returned = cache.getCityAirQuality("Aveiro");
        assertThat(returned.getCity_name()).isEqualTo(aveiro_airquality.getCity_name());
        assertThat(returned.getLat()).isEqualTo(aveiro_airquality.getLat());
        assertThat(returned.getLon()).isEqualTo(aveiro_airquality.getLon());
        assertThat(returned.getData()[0]).isEqualTo(aveiro_airquality.getData()[0]);
    }

    @Test
    public void getCoordinatesAirQuality_Test() {
        AirQuality aveiro_airquality = new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"Aveiro","40.64427","-8.64554", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)});
        cache.saveCityAirQuality(aveiro_airquality.getCity_name(), aveiro_airquality);
        AirQuality returned = cache.getCoordinatesAirQuality("40.64427","-8.64554");
        assertThat(returned.getCity_name()).isEqualTo(aveiro_airquality.getCity_name());
        assertThat(returned.getLat()).isEqualTo(aveiro_airquality.getLat());
        assertThat(returned.getLon()).isEqualTo(aveiro_airquality.getLon());
        assertThat(returned.getData()[0]).isEqualTo(aveiro_airquality.getData()[0]);
    }

    @Test
    public void saveCityAirQuality_Test() {
        String city1 = "Braga";
        String city2 = "Aveiro";
        AirQuality air = new AirQuality();
        AirQuality air2 = new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"Aveiro","40.64427","-8.64554", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)});
        cache.saveCityAirQuality(city1, air);
        cache.saveCityAirQuality(city2, air2);
        Map<String, AirQuality> memory_cache = cache.getCacheMemory();
        assertThat(memory_cache)
                .hasSize(2)
                .containsKeys("braga","aveiro")
                .containsValues(air,air2);
    }
    @Test
    public void isValidCityFalse_Test() {
        //Not in Cache
        boolean validVerification = cache.isValidCity("city");
        assertEquals(false,cache.getCacheMemory().containsKey("city"));
        assertEquals(false,validVerification);

        //Doesn't respect the time-to-live policy
        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        AirQuality air = new AirQuality();
        air.setTimestamp(700000);
        cache.saveCityAirQuality("Coimbra", air);
        boolean time_to_live_verification = cache.isValidCity("Coimbra");
        assertEquals(false,currentTime - cache.getCacheMemory().get("coimbra").getTimestamp() < 600000);
        assertEquals(false,time_to_live_verification);

    }

    @Test
    public void isValidCoordinatesFalse_Test(){
        //Not in Cache
        boolean validVerification = cache.isValidCoordinates("2","2");
        boolean compare =false;
        for (AirQuality x : cache.getCacheMemory().values()) {
            if (x.getLat().equals("2") && x.getLon().equals("2")) {
                compare=true;
            }
        }
        assertEquals(false,compare);
        assertEquals(false,validVerification);

        //Doesn't respect the time-to-live policy
        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        AirQuality air = new AirQuality();
        air.setTimestamp(700000);
        air.setCity_name("city");
        cache.saveCityAirQuality(air.getCity_name(), air);
        boolean time_to_live_verification = cache.isValidCity(air.getCity_name());
        assertEquals(false,currentTime - cache.getCacheMemory().get(air.getCity_name()).getTimestamp() < 600000);
        assertEquals(false,time_to_live_verification);
    }


    @Test
    public void isValidCityTrue_Test() {
        //In Cache and respecting time-to-live policy
        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        AirQuality air = new AirQuality();
        air.setTimestamp(currentTime-500000);
        cache.saveCityAirQuality("Coimbra", air);

        //In Cache
        assertEquals(false,cache.getCacheMemory().containsKey("city"));

        //Respecting time-to-live policy
        assertEquals(true,currentTime - cache.getCacheMemory().get("coimbra").getTimestamp() < 600000);

        //In Cache and respecting time-to-live policy
        boolean time_to_live_verification = cache.isValidCity("coimbra");
        assertEquals(true,time_to_live_verification);
    }

    @Test
    public void isValidCoordinatesTrue_Test(){

        long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        AirQuality air = new AirQuality();
        air.setTimestamp(currentTime-500000);
        air.setCity_name("city");
        air.setLat("1");
        air.setLon("1");
        cache.saveCityAirQuality(air.getCity_name(),air);

        //In Cache
        boolean compare =false;
        for (AirQuality x : cache.getCacheMemory().values()) {
            if (x.getLat().equals("1") && x.getLon().equals("1")) {
                compare=true;
            }
        }
        assertEquals(true,compare);

        //Respecting time-to-live policy
        assertEquals(true,currentTime - cache.getCacheMemory().get(air.getCity_name()).getTimestamp() < 600000);

        //In Cache and respecting time-to-live policy
        boolean time_to_live_verification = cache.isValidCoordinates("1","1");
        assertEquals(true,time_to_live_verification);
    }

    @Test
    void setHitMiss_Test(){
        int hit = 0;
        int miss = 0;
        // Get City AirQuality not present in Cache
        cache.setMiss();
        miss++;
        AirQuality air = new AirQuality();
        air.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        cache.saveCityAirQuality("Porto", air);
        //Make new Get
        cache.setHit();
        hit++;
        //Get Statistics (counts has a hit)
        Map<String, String> found = cache.getStatistics();
        hit++;
        HashMap<String, String> expected = new HashMap<>();
        expected.put("miss", String.valueOf(miss));
        expected.put("hit", String.valueOf(hit));
        expected.put("citiesAirInfoInCache", cache.getCacheMemory().keySet().toString());
        assertThat(expected).isEqualTo(found);
    }

    @Test
    void getStatistics_Test(){
        Map<String, String> found = cache.getStatistics();
        HashMap<String, String> expected = new HashMap<>();
        expected.put("miss", String.valueOf(0));
        expected.put("hit", String.valueOf(1));
        expected.put("citiesAirInfoInCache", cache.getCacheMemory().keySet().toString());
        assertThat(expected).isEqualTo(found);
    }


}
