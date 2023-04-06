package com.example.Neurosurgical.App.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "professors")
public class ProfessorEntity {
    @Id
    private Long idUser;

    @Column(name="code")
    private String code;

    @Column(name="degree")
    private String degree;
}