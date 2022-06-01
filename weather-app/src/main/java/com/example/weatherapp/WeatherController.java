package com.example.weatherapp;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private LocationService locationService;
    private Coordinates locationEntry = new Coordinates();

    @GetMapping("/weather")
    public String getLocation(Model model) throws IOException, ParseException {

        if(weatherService.getLocation() == null)
        {
            weatherService.setLocation(weatherService.paris);
        }

        model.addAttribute("locationService",locationService);
        model.addAttribute("locationModel", new LocationModel());
        model.addAttribute("weather", weatherService);

        return "home";
    }

    @PostMapping("/weather")
    public String setLocation(@ModelAttribute ("locationModel") LocationModel locationModel, Model model) throws IOException, ParseException
    {
        locationModel.getCity();

        locationService.setCity(locationModel.getCity());
        locationService.setState(locationModel.getState());
        locationService.setCountry(locationModel.getCountry());
        weatherService.setLocation(locationService.getLocation());

        model.addAttribute("humidity", "Humidity: " + weatherService.getWeather().getHumidity());
        model.addAttribute("temp", "Temp: " + weatherService.getWeather().getTemp() + "F\u00B0");
        model.addAttribute("temp_min", "Min Temp: " + weatherService.getWeather().getTempMin() + "F\u00B0");
        model.addAttribute("temp_max", "Max Temp: " + weatherService.getWeather().getTempMax() + "F\u00B0");
        model.addAttribute("weather_description", "Weather: " + weatherService.getWeather().getWeatherDescription());

        return "info";
    }





}
