package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "questions_exam")
public class ExamQuestionEntity extends BaseEntity {
    @Column(name = "question_text")
    private String questionText;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private ProfessorEntity professor;

    @Column(name = "points")
    private int points;

    /* TODO ExamHasQuestions/AnswerExam/CorrectAnswerExam - Entity
    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<QuizzHasQuestionsEntity> quizzHasQuestions;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<AnswerQuizzEntity> answersQuestion;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "question")
    private List<CorrectAnswerQuizzEntity> correctAnswersQuestion;
    */

    public ExamQuestionEntity(){
        //TODO Constructor
    }
}
