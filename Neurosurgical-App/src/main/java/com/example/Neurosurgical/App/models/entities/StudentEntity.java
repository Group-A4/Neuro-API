package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private String code;

    @Column(name="year")
    private int year;

    @Column(name="semester")
    private int semester;

    @Column(name="birth_date")
    private Timestamp birthDate;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<StudentFollowsCoursesEntity> registrations;
}
