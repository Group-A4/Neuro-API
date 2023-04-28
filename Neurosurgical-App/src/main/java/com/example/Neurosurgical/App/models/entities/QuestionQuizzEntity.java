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
@Table(name = "questions_quizz")
public class QuestionQuizzEntity extends BaseEntity {

    @Column(name = "question_text")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private ProfessorEntity professor;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<QuizzHasQuestionsEntity> quizzHasQuestions;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerQuizzEntity> answersQuestion;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<CorrectAnswerQuizzEntity> correctAnswersQuestion;

    public QuestionQuizzEntity(){

        this.quizzHasQuestions = new ArrayList<>();
        this.answersQuestion = new ArrayList<>();
        this.correctAnswersQuestion = new ArrayList<>();

    }

}
