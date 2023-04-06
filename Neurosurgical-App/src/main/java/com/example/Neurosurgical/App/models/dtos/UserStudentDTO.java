package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserStudentDTO {
    private Long id;
    private String password;
    private String lastName;
    private String firstName;
    private String emailFaculty;
    private String emailPersonal;
    private int role;
    private String code;
    private int year;
    private int semester;
    private LocalDate birthDate;

}
