package com.example.weather.models.openweather;

import lombok.Data;

@Data
public class SystemParameters {
    private String countryCode;
    private long sunriseTime;
    private long sunsetTime;
}
