package com.example.Neurosurgical.App.models.entities;


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
    CourseEntity course;
}
