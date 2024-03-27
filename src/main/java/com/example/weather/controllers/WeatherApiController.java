package com.example.weather.controllers;

import com.example.weather.DTO.LocationDTO;
import com.example.weather.DTO.SessionDTO;
import com.example.weather.DTO.UserDTO;
import com.example.weather.models.Location;
import com.example.weather.models.Session;
import com.example.weather.models.User;
import com.example.weather.models.openweather.SearchResult;
import com.example.weather.models.openweather.WeatherData;
import com.example.weather.services.LocationService;
import com.example.weather.services.SessionService;
import com.example.weather.services.UserService;
import com.example.weather.services.WeatherApiClient;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WeatherApiController {
    private final WeatherApiClient weatherApiClient;
    private final LocationService locationService;
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    WeatherApiController(WeatherApiClient weatherApiClient, LocationService locationService,
                         UserService userService, SessionService sessionService) {
        this.weatherApiClient = weatherApiClient;
        this.locationService = locationService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping(path = "/api/weather/{locationId}")
    public ResponseEntity<WeatherData> getWeatherData(@PathVariable Long locationId) {
        Location location = locationService.getLocationById(locationId).orElseThrow(() -> new EntityNotFoundException(
                String.format("Location with id %d not found", locationId)));
        WeatherData weatherData = weatherApiClient.getWeatherData(location);
        return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

        @GetMapping(path = "/search/{locationName}")
    public String showSearchLocationResultsPage(@PathVariable String locationName, Model model) {
        ResponseEntity<List<SearchResult>> response = weatherApiClient.getLocationByName(locationName);
        List<SearchResult> searchResults = response.getBody();
        List<LocationDTO> locationDTOList = searchResults.stream()
                .map(searchResult -> LocationDTO.mapFromSearchResult(searchResult, 1L)).toList();
        model.addAttribute("searchResults", locationDTOList);
        return "search_results";
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
    public ResponseEntity<?> saveLocation(@ModelAttribute LocationDTO locationDTO) {
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

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users =userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/user/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("No user with id %d", userId)));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/user")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return new ResponseEntity<>("UserDTO is null", HttpStatus.BAD_REQUEST);
        }

        if (userDTO.getEmail().isEmpty() ||  userDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Email and password are required fields", HttpStatus.BAD_REQUEST);
        }

        User userToSave = new User();
        userToSave.setEmail(userDTO.getEmail());
        userToSave.setPassword(userDTO.getPassword());
        User savedUser = userService.saveUser(userToSave);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/user/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        User userToDelete = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with id %d not found", userId)));
        String userEmail = userToDelete.getEmail();
        userService.deleteUser(userId);
        return new ResponseEntity<>("User with email " + userEmail + "deleted successfully", HttpStatus.OK);
    }

    @GetMapping(path = "/sessions")
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @GetMapping(path = "/session/id/{sessionId}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long sessionId) {
        Session session = sessionService.getSessionById(sessionId).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("No session with id %d", sessionId)));
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @PostMapping(path = "/session")
    public ResponseEntity<?> saveSession(@RequestBody SessionDTO sessionDTO) {
        if (sessionDTO == null) {
            return new ResponseEntity<>("SessionDTO is null", HttpStatus.BAD_REQUEST);
        }

        if (sessionDTO.getUserId() == null || sessionDTO.getExpirationTime() == null) {
            return new ResponseEntity<>("User ID and expiration time are required fields", HttpStatus.BAD_REQUEST);
        }

        Session sessionToSave = new Session();
        sessionToSave.setUser(userService.getUserById(sessionDTO.getUserId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("No user with id %d", sessionDTO.getUserId()))));
        sessionToSave.setExpirationTime(sessionDTO.getExpirationTime());
        sessionService.saveSession(sessionToSave);
        return new ResponseEntity<>(sessionToSave, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/session/delete/{sessionId}")
    public ResponseEntity<String> deleteSessionById(@PathVariable Long sessionId) {
        Session sessionToDelete = sessionService.getSessionById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Session with id %d not found", sessionId)));
        Long userId = sessionToDelete.getUser().getId();
        sessionService.deleteSession(sessionId);
        return new ResponseEntity<>("Session with user ID " + userId + "deleted successfully", HttpStatus.OK);
    }
}
