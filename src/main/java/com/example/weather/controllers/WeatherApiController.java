package com.example.weather.controllers;

import com.example.weather.models.Location;
import com.example.weather.models.openweather.WeatherData;
import com.example.weather.services.LocationService;
import com.example.weather.services.WeatherApiClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherApiController {
    private final WeatherApiClient weatherApiClient;
    private final LocationService locationService;
    @Autowired
    WeatherApiController(WeatherApiClient weatherApiClient, LocationService locationService) {
        this.weatherApiClient = weatherApiClient;
        this.locationService = locationService;
    }

    @GetMapping(path = "/api/weather/{locationId}")
public ResponseEntity<WeatherData> getWeatherData(@PathVariable Long locationId) {
        Location location = locationService.getLocationById(locationId).orElseThrow(() -> new EntityNotFoundException(
                String.format("Location with id %d not found", locationId)));
        WeatherData weatherData = weatherApiClient.getWeatherData(location);
        return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

}
