package com.example.Neurosurgical.App.model.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "students")
public class StudentEntity  {
    @Id
    private Long idUser;

    @Column(name="code")
    private String code;

    @Column(name="year")
    private int year;

    @Column(name="semester")
    private int semester;

    @Column(name="birth_date")
    private Timestamp birthDate;
}
