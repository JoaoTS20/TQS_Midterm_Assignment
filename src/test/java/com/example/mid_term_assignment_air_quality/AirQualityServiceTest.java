package com.example.mid_term_assignment_air_quality;

import com.example.mid_term_assignment_air_quality.Cache.Cache;
import com.example.mid_term_assignment_air_quality.entities.AirQuality;
import com.example.mid_term_assignment_air_quality.entities.AirQualityData;
import com.example.mid_term_assignment_air_quality.services.AirQualityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
 class AirQualityServiceTest {

    @Mock(lenient = true)
    private Cache cache;

    @InjectMocks
    private AirQualityService airQualityService;

    @BeforeEach
    public void setUp() {
        Map<String, AirQuality> cache_memory = new HashMap<>();

        cache_memory.put("Porto", new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"porto","42.16737","-6.89934", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)}));
        cache_memory.put("Coimbra", new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"coimbra","40.20564","-8.41955", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)}));
        AirQuality aveiro =  new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"Aveiro","40.64427","-8.64554", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)});
        cache_memory.put("Aveiro",aveiro);

        HashMap<String, String> statistics = new HashMap<>();
        statistics.put("hit", "5");
        statistics.put("miss", "3");
        statistics.put("citiesAirInfoInCache", cache_memory.keySet().toString());

        Mockito.when(cache.getCacheMemory()).thenReturn(cache_memory);
        Mockito.when(cache.getStatistics()).thenReturn(statistics);
        Mockito.when(cache.getCityAirQuality("Aveiro")).thenReturn(aveiro);
        Mockito.when(cache.getCoordinatesAirQuality("40.64427","-8.64554")).thenReturn(aveiro);
        Mockito.when(cache.getCityAirQuality("xxxx")).thenReturn(new AirQuality(0,"error","","", new AirQualityData[]{}));
        Mockito.when(cache.getCoordinatesAirQuality("200","200")).thenReturn(new AirQuality(0,"error","","", new AirQualityData[]{}));

    }
    @Test
     void whenValidCity_thenAirQualityReturned() {
        AirQuality airQualityAveiro = airQualityService.getCityAirQuality("Aveiro");
        AirQualityData data = airQualityAveiro.getData()[0];
        assertEquals(1,airQualityAveiro.getData().length);
        assertEquals("Aveiro",airQualityAveiro.getCity_name());
        assertEquals("40.64427",airQualityAveiro.getLat());
        assertEquals("-8.64554",airQualityAveiro.getLon());
        assertEquals(1.2, data.getAqi());
        assertEquals(2.1, data.getO3());
        assertEquals(3.1, data.getCo());
        assertEquals(1.0, data.getSo2());
        assertEquals(2.1, data.getNo2());
        assertEquals(2.0, data.getPm25());
        assertEquals(3.0, data.getPm10());
        assertEquals(1, data.getPollen_level_tree());
        assertEquals(2, data.getPollen_level_grass());
        assertEquals(1, data.getPollen_level_weed());
        assertEquals(1, data.getMold_level());
    }

    @Test
     void whenValidCoordinates_thenAirQualityReturned() {
        AirQuality airQualityAveiro = airQualityService.getCoordinatesAirQuality(40.64427,-8.64554);
        AirQualityData data = airQualityAveiro.getData()[0];
        assertEquals(1,airQualityAveiro.getData().length);
        assertEquals("Aveiro",airQualityAveiro.getCity_name());
        assertEquals("40.64427",airQualityAveiro.getLat());
        assertEquals("-8.64554",airQualityAveiro.getLon());
        assertEquals(1.2, data.getAqi());
        assertEquals(2.1, data.getO3());
        assertEquals(3.1, data.getCo());
        assertEquals(1.0, data.getSo2());
        assertEquals(2.1, data.getNo2());
        assertEquals(2.0, data.getPm25());
        assertEquals(3.0, data.getPm10());
        assertEquals(1, data.getPollen_level_tree());
        assertEquals(2, data.getPollen_level_grass());
        assertEquals(1, data.getPollen_level_weed());
        assertEquals(1, data.getMold_level());
    }

    @Test
     void whenInvalidCity_thenAirQualityErrorReturned() {
        AirQuality airQualityx = airQualityService.getCityAirQuality("xxxx");
        assertEquals(0,airQualityx.getData().length);
        assertEquals("error",airQualityx.getCity_name());
        assertEquals("",airQualityx.getLat());
        assertEquals("",airQualityx.getLon());
    }
    @Test
     void whenInvalidCoordinates_thenAirQualityErrorReturned() {
        AirQuality airQualityx = airQualityService.getCoordinatesAirQuality(200,200);
        assertEquals(0,airQualityx.getData().length);
        assertEquals("error",airQualityx.getCity_name());
        assertEquals("",airQualityx.getLat());
        assertEquals("",airQualityx.getLon());
    }

    @Test
     void givenStatistics_whenGetStatistics_thenReturnValues() {
        Set<String> set = new HashSet<>(Arrays.asList("Porto", "Aveiro", "Coimbra"));
        int miss = 3;
        int hit = 5;

        Map<String, String> statistics = airQualityService.getStatistics();
        assertThat(statistics)
                .hasSize(3)
                .containsKeys("hit", "miss", "citiesAirInfoInCache")
                .containsValues(String.valueOf(hit), String.valueOf(miss), set.toString());
    }

    @Test
     void givenMemory_Cache_whenGetCache_thenReturn() {
        String city0 ="Porto";
        String city1 = "Coimbra";
        String city2 = "Aveiro";
        Map<String,AirQuality> all = airQualityService.getCache();
        assertThat(all)
                .hasSize(3)
                .containsValues(all.get(city0),all.get(city1),all.get(city2))
                .containsKeys(city0, city1, city2);
    }





}
