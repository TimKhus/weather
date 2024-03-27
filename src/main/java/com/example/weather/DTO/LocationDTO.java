package com.example.weather.DTO;

import com.example.weather.models.openweather.SearchResult;
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

    public static LocationDTO mapFromSearchResult(SearchResult searchResult, Long userId) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName(searchResult.getName());
        locationDTO.setLatitude(searchResult.getLat());
        locationDTO.setLongitude(searchResult.getLon());
        locationDTO.setUserId(userId);
        return locationDTO;
    }
}
