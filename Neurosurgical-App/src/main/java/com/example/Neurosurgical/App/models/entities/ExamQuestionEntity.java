package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "questions_exam")
public class ExamQuestionEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_exam")
    private ExamEntity exam;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private ProfessorEntity professor;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "points")
    private int points;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<ExamAnswerEntity> answersQuestion;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<CorrectExamAnswerEntity> correctAnswersQuestion;

    public ExamQuestionEntity(){
        this.answersQuestion = new ArrayList<>();
        this.correctAnswersQuestion = new ArrayList<>();
    }
}