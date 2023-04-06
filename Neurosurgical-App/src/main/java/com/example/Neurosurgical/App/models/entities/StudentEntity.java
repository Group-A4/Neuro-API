package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

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
    private Integer year;

    @Column(name="semester")
    private Integer semester;

    @Column(name="birth_date")
    private Timestamp birth_date;
}