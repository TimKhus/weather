package com.example.weather.models.openweather;

import lombok.Data;

@Data
public class MainParameters {
    private double temperature;
    private double feelsLike;
    private int pressure;
    private int humidity;
    private int seaLevel;
    private int groundLevel;
}
