package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@Builder
public class Student {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id_users;
    @Column(name="code")
    private String code;
    @Column(name="year")
    private int year;
    @Column(name="semester")
    private int semester;
    @Column(name="birth_date")
    private LocalDate birthDate;
}
