package com.example.weather.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationDTO {
    @NotNull
    private String name;

    @NotNull
    private Long userId;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

}
