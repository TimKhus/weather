package com.example.weather.services;

import com.example.weather.models.Session;
import com.example.weather.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }
    public Optional<Session> getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }
    public Optional<Session> getSessionByUserId(Long userId) {
        return sessionRepository.findByUserId(userId);
    }
    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }
    public String deleteSession(Long sessionId) {
        Session sessionToDelete = sessionRepository.findById(sessionId).orElseThrow();
        String deleteMessage = String.format("Session with Id %d and User Id %d was successfully deleted",
                sessionId, sessionToDelete.getUserId());
        sessionRepository.delete(sessionToDelete);
        return deleteMessage;
    }
}
