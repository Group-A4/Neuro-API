package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "students")
public class StudentEntity  {
    @Id
    private Long idUser;

    @Column(name="code", unique = true)
    @NotNull(message = "Code cannot be null")
    private String code;

    @Column(name="year")
    @Min(value = 1, message = "Year should not be less than 1")
    @Max(value = 10, message = "Year should not be greater than 10")
    private int year;

    @Column(name="semester")
    @Min(value = 1, message = "Semester should not be less than 1")
    @Max(value = 2, message = "Semester should not be greater than 2")
    private int semester;

    @Column(name="birth_date")
    @Past
    private Timestamp birthDate;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "student")
    private List<StudentFollowsCoursesEntity> enrollments;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<StudentPointsEntity> studentPoints;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<LongAnswerResponsesEntity> longAnswerResponses;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<StudentTookExamsEntity> studentTookExams;
}
