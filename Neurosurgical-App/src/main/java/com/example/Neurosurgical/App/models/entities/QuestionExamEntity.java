package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "questions_exam")
public class QuestionExamEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_exam", nullable = false)
    private ExamEntity exam;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private ProfessorEntity professor;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "points")
    private Double points;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerExamEntity> answersQuestion;


    public QuestionExamEntity() {
        this.answersQuestion = new ArrayList<>();
    }
}
