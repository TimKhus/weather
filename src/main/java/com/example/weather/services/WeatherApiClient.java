package com.example.weather.services;

import com.example.weather.models.Location;
import com.example.weather.models.openweather.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiClient {
        private final String apiUrl = "https://api.openweathermap.org/";
    private final String apiKey = "655628e636e79e03375daf04372ebe26";

    private final RestTemplate restTemplate;


    @Autowired
    public WeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherData getWeatherData(Location location) {
        String url = apiUrl + "data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() +
                "&appid=" + apiKey + "&units=metric";
        return restTemplate.getForObject(url, WeatherData.class);
    }
}
