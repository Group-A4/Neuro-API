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

    @Column(name = "id_exam", nullable = false)
    private Long idExam;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false)
    private CourseEntity idCourse;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private ProfessorEntity idProfessor;

    @Column(name = "question_text")
    private String questionTest;

    @Column(name = "points")
    private Integer points;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerExamEntity> answersQuestion;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<CorrectAnswerExamEntity> correctAnswersQuestion;

    public QuestionExamEntity() {
        this.answersQuestion = new ArrayList<>();
        this.correctAnswersQuestion = new ArrayList<>();
    }
}
