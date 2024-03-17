package com.example.weather.controllers;

import com.example.weather.DTO.UserDTO;
import com.example.weather.models.User;
import com.example.weather.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
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

        if (userDTO.getEmail().isEmpty() || userDTO.getEmail() == null || userDTO.getPassword().isEmpty()
                || userDTO.getPassword() == null) {
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
}
