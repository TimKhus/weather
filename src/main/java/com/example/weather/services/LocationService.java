package com.example.weather.services;

import com.example.weather.models.Location;
import com.example.weather.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationByName(String name) {
        return locationRepository.findByName(name);
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public String deleteLocation(Long id) {
        Location locationToDelete = locationRepository.findById(id).orElseThrow();
        String deleteMessage = String.format("Location with id %d and name %s was successfully deleted",
                id, locationToDelete.getName());
        locationRepository.deleteById(id);
        return deleteMessage;
    }
}
