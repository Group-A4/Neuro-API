package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "student_points")
public class StudentPointsEntity {
    @Id
    private Long id;
    
    @Column(name = "points")
    private Long code;
    
    @ManyToOne
    @JoinColumn(name = "id_student", nullable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "id_exam")
    private ExamEntity exam;
    
    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionExamEntity question;
}
