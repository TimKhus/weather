package com.example.weather.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne(mappedBy = "user")
    private User user;

    @NotNull
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
}
