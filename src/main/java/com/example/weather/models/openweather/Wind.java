package com.example.weather.models.openweather;

import lombok.Data;

@Data
public class Wind {
    private double speed;
    private int direction;
    private double gust;
}
