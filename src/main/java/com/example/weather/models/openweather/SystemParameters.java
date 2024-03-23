package com.example.weather.models.openweather;

import lombok.Data;

@Data
public class SystemParameters {
    private String country;
    private long sunrise;
    private long sunset;
}
