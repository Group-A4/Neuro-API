package com.example.Neurosurgical.App.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorCreationDto {
    private String lastName;

    private String firstName;

    private String code;

    private String emailFaculty;

    private String emailPersonal;

    private String degree;

    private String password;
}
