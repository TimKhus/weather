package com.example.weather.controllers;

import com.example.weather.DTO.LocationDTO;
import com.example.weather.models.Location;
import com.example.weather.services.LocationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
class LocationController {

    private final LocationService locationService;

    @Autowired
    LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(path = "/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping(path = "/location/id/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long locationId) {
        Location location = locationService.getLocationById(locationId).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Location with id %d not found", locationId)));
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping(path = "/location")
    public ResponseEntity<?> saveLocation(@RequestBody LocationDTO locationDTO) {
        if (locationDTO == null) {
            return new ResponseEntity<>("LocationDTO is null", HttpStatus.BAD_REQUEST);
        }

        if (locationDTO.getName().isEmpty() || locationDTO.getUserId() == null || locationDTO.getLatitude() == null
                || locationDTO.getLongitude() == null) {
            return new ResponseEntity<>("Name, User ID, latitude and longitude are required fields",
                    HttpStatus.BAD_REQUEST);
        }

        Location locationToSave = new Location();
        locationToSave.setName(locationDTO.getName());
        locationToSave.setUserId(locationDTO.getUserId());
        locationToSave.setLatitude(locationDTO.getLatitude());
        locationToSave.setLongitude(locationDTO.getLongitude());
        locationService.saveLocation(locationToSave);
        return new ResponseEntity<>(locationToSave, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/location/delete/{locationId}")
    public ResponseEntity<String> deleteLocationById(@PathVariable Long locationId) {
        Location locationToDelete = locationService.getLocationById(locationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Location with id %d not found", locationId)));
        String locationName = locationToDelete.getName();
        locationService.deleteLocation(locationId);
        return new ResponseEntity<>("Location with name " + locationName + "deleted successfully", HttpStatus.OK);
    }
}
