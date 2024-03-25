package com.example.weather.models.openweather;

import lombok.Data;

@Data
public class SearchResult {
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;
}
