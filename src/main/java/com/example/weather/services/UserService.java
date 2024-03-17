package com.example.weather.services;

import com.example.weather.models.User;
import com.example.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public String deleteUser(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow();
        String deleteMessage = String.format("User with id %d and email %s was successfully deleted",
                id, userToDelete.getEmail());
        userRepository.deleteById(id);
        return deleteMessage;
    }

}
