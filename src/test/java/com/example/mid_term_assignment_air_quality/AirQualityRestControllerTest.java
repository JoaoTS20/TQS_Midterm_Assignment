package com.example.mid_term_assignment_air_quality;

import com.example.mid_term_assignment_air_quality.entities.AirQuality;
import com.example.mid_term_assignment_air_quality.entities.AirQualityData;
import com.example.mid_term_assignment_air_quality.services.AirQualityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
 class AirQualityRestControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AirQualityService airQualityService;

    @Test
     void getAirQualityCity_Test() throws Exception{
        given(airQualityService.getCityAirQuality(Mockito.anyString())).willReturn(new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"city","2","1", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)}));
        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/city/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("city"))
                .andExpect(jsonPath("data[0].pm10").value(3.0));
        verify(airQualityService, times(1)).getCityAirQuality(Mockito.anyString());
    }

    @Test
     void getAirQualityCoordinates_Test() throws Exception{
        given(airQualityService.getCoordinatesAirQuality(Mockito.anyDouble(),Mockito.anyDouble())).willReturn(new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"city","41.14961","-8.61099", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)}));
        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/coordinates/"+41.149612+","+21.01))
                .andExpect(status().isOk())
                .andExpect(jsonPath("lat").value("41.14961"))
                .andExpect(jsonPath("lon").value("-8.61099"))
                .andExpect(jsonPath("data[0].pm10").value(3.0));
        verify(airQualityService, times(1)).getCoordinatesAirQuality(Mockito.anyDouble(),Mockito.anyDouble());
    }


    @Test
     void getAirQualityInvalidCity_Test() throws Exception{
        given(airQualityService.getCityAirQuality(Mockito.anyString())).willReturn(new AirQuality(0,"error","","", new AirQualityData[]{}));
        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/city/fake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("error"))
                .andExpect(jsonPath("lat").value(""))
                .andExpect(jsonPath("lon").value(""));
        verify(airQualityService, times(1)).getCityAirQuality(Mockito.anyString());

    }


    @Test
     void getAirQualityInvalidCoordinates_Test() throws Exception{
        given(airQualityService.getCoordinatesAirQuality(Mockito.anyDouble(),Mockito.anyDouble())).willReturn(new AirQuality(0,"error","","", new AirQualityData[]{}));
        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/coordinates/"+200+","+200))
                .andExpect(status().isOk())
                .andExpect(jsonPath("city_name").value("error"))
                .andExpect(jsonPath("lat").value(""))
                .andExpect(jsonPath("lon").value(""));
        verify(airQualityService, times(1)).getCoordinatesAirQuality(Mockito.anyDouble(),Mockito.anyDouble());

    }

    @Test
     void getCache_Test() throws Exception{
        Map<String, AirQuality> cache_memory = new HashMap<>();
        cache_memory.put("porto", new AirQuality(new Timestamp(System.currentTimeMillis()).getTime(),"porto","-6.90","42.17", new AirQualityData[]{new AirQualityData(1.2,2.1,3.1,1.0,2.1,2.0,3.0,1,2,1,1)}));
        given(airQualityService.getCache()).willReturn(cache_memory);
        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/cached"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("porto.timestamp").value(cache_memory.get("porto").getTimestamp()))
                .andExpect(jsonPath("porto.city_name").value(cache_memory.get("porto").getCity_name()))
                .andExpect(jsonPath("porto.lat").value(cache_memory.get("porto").getLat()))
                .andExpect(jsonPath("porto.lon").value(cache_memory.get("porto").getLon()));
        verify(airQualityService, times(1)).getCache();
    }

    @Test
     void getStatistics_Test() throws Exception{
        Set<String> set = new HashSet<>(Arrays.asList("Porto", "Aveiro", "Coimbra"));
        HashMap<String, String> statistics = new HashMap<>();
        statistics.put("hit", "5");
        statistics.put("miss", "3");
        statistics.put("citiesAirInfoInCache", set.toString());
        given(airQualityService.getStatistics()).willReturn(statistics);
        mvc.perform(MockMvcRequestBuilders.get("/api/airquality/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("miss").value(statistics.get("miss")))
                .andExpect(jsonPath("hit").value(statistics.get("hit")))
                .andExpect(jsonPath("citiesAirInfoInCache").value(statistics.get("citiesAirInfoInCache")));
        verify(airQualityService, times(1)).getStatistics();
    }


}
