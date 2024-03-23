package com.example.weather.models.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Rain {
    @JsonProperty("1h")
    private double h1;

    @JsonProperty("3h")
    private double h3;
}
