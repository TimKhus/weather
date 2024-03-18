package com.example.weather.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionDTO {
    @NotNull
    private Long userId;

    @NotNull
    private LocalDateTime expirationTime;
}
