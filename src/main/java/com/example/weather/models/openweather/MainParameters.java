package com.example.weather.models.openweather;

import lombok.Data;

@Data
public class MainParameters {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;
    private int sea_level;
    private int grnd_level;
}
