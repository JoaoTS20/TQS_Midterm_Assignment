package com.example.mid_term_assignment_air_quality;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

 class AllComponentsTest {

    @Test
    @Order(1)
    void getCityTest(){
        String baseURI = "http://localhost:8080/api/airquality/city/Porto";
        given().relaxedHTTPSValidation().when().get(baseURI).then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("city_name",is("Porto"))
                .body("lat",is("42.16737"))
                .body("lon",is("-6.89934"));
    }

    @Test
    @Order(2)
    void getCoordinatesTest(){
        String baseURI = "http://localhost:8080/api/airquality/coordinates/40.64427,-8.64554";

        given().relaxedHTTPSValidation().when().get(baseURI).then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("city_name",is("Aveiro"))
                .body("lat",is("40.64427"))
                .body("lon",is("-8.64554"));
    }

}
