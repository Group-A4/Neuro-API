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
public class StudentDto {
    private String lastName;

    private String firstName;

    private String code;

    private String emailFac;

    private String emailPersonal;

    private int year;

    private int semester;

    private int dataNastere;
}
