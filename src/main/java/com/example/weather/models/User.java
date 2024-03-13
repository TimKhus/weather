package com.example.weather.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotBlank
    @Column(name = "email", nullable = false)
    String email;

    @NotBlank
    @Column(name = "password", nullable = false)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$",
    message = "Password must be at least 6 characters long and contain at least one lowercase letter, one uppercase letter, and one digit.")
    String password;
}
