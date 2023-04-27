package com.example.Neurosurgical.App.model.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "didactic")
@Builder
public class DidacticEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_professor")
    ProfessorEntity professor;

    @ManyToOne
    @JoinColumn(name = "id_course")
    com.example.Neurosurgical.App.models.entities.CourseEntity course;
}
