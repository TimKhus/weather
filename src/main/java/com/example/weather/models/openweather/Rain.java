package com.example.weather.models.openweather;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Rain {
    @JsonProperty("1h")
    private double h1;

    @JsonProperty("3h")
    private double h3;
}
