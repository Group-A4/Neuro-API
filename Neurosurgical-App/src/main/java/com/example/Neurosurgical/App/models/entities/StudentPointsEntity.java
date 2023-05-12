package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

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
    private ExamQuestionEntity question;
}
