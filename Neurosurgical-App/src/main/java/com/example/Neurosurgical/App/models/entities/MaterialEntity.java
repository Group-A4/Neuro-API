package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materials")
@Builder
public class MaterialEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_course")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private ProfessorEntity professor;

    @Column(name="title")
    private String title;

    @Column(name="link")
    private String link;
}
