package com.example.weather.models.openweather;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
