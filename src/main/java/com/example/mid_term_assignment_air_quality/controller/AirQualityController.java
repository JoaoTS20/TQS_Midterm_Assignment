package com.example.mid_term_assignment_air_quality.controller;

import com.example.mid_term_assignment_air_quality.services.AirQualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AirQualityController {

    List<String> cities =  new ArrayList<>(Arrays.asList("Viana-do-Castelo", "Braga", "VilaReal", "Bragança", "Porto", "Aveiro", "Viseu", "Guarda",
            "Coimbra", "CasteloBranco", "Leiria", "Santarém", "Lisboa", "Portalegre", "Évora", "Setubal",
            "Beja", "Faro", "Funchal", "PontaDelgada"));

    @Autowired
    private AirQualityService airQualityService;

    @GetMapping("")
    public String home(@RequestParam(required=false) String city, Model model) {
        model.addAttribute("locations", cities );
        if (city!=null) {
            model.addAttribute("selected", true);
            model.addAttribute("airQuality", airQualityService.getCityAirQuality(city));
        }
        else
            model.addAttribute("selected", false );
        return "index";
    }


}
