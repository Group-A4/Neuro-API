package com.example.Neurosurgical.App.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String lastName;

    private String firstName;

    private String emailFac;

    private String emailPersonal;
}
