package com.example.mid_term_assignment_air_quality;


import com.example.mid_term_assignment_air_quality.controller.AirQualityController;
import com.example.mid_term_assignment_air_quality.services.AirQualityService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

@WebMvcTest(AirQualityController.class)
public class AirQualityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AirQualityService airQualityService;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void home__test() throws Exception {
        RestAssuredMockMvc.given().auth().none().when().get("").then()
                .log().all().statusCode(200)
                .contentType(ContentType.HTML)
                .body("html.head.title",equalTo("Air Quality"));
    }
}
