package com.example.weather.models.openweather;

import lombok.Data;

@Data
public class WeatherData {
    private Coordinates coord;
    private Weather[] weather;
    private MainParameters main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    private long dt;
    private SystemParameters sys;
    private int timezone;
    private int id;
    private String name;
}
