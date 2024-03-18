package com.example.weather.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
