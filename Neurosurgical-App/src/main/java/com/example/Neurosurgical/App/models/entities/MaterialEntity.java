package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materials")
@Builder
public class MaterialEntity extends BaseEntity{
    @Column(name="id_course")
    private Long idCourse;

    @Column(name="id_professor")
    private Long idProfessor;

    @Column(name="title")
    private String title;

    @Column(name="link")
    private String link;
}
