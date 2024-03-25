package com.example.weather.services;

import com.example.weather.models.Location;
import com.example.weather.models.openweather.SearchResult;
import com.example.weather.models.openweather.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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

    public ResponseEntity<List<SearchResult>> getAllLocations(String locationName) {
        String url = apiUrl + "geo/1.0/direct?q=" + locationName + "&limit=3&appid=" + apiKey;
        ResponseEntity<SearchResult[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                SearchResult[].class
        );
        List<SearchResult> searchResults = Arrays.asList(responseEntity.getBody());
        return new ResponseEntity<>(searchResults, responseEntity.getStatusCode());
    }
}
