package com.example.Neurosurgical.App.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@Data
@NoArgsConstructor
public class UserLoginDto {
    @NotNull(message = "Email cannot be null !")
    private String emailFac;
    @NotNull(message = "Password cannot be null !")
    private String password;
}
