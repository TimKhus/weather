package com.example.weather.controllers;

import com.example.weather.DTO.SessionDTO;
import com.example.weather.models.Session;
import com.example.weather.services.SessionService;
import com.example.weather.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SessionController {

    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    SessionController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
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
