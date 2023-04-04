package com.example.Neurosurgical.App.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Entity
@Table(name = "professors")
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorEntity extends UserEntity {

    @Column(name = "id")
    private Long id;
}
