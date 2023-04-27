package com.example.Neurosurgical.App.model.entity;

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
@Table(name = "answers_quizz")
public class AnswerQuizzEntity extends BaseEntity{
    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionQuizzEntity question;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "answer")
    private List<CorrectAnswerQuizzEntity> correctAnswerQuizz;

    public AnswerQuizzEntity(){
        this.correctAnswerQuizz = new ArrayList<>();
    }

}
