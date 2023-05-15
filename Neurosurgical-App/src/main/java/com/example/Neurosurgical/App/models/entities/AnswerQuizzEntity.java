package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answers_quizz")
public class AnswerQuizzEntity extends BaseEntity {
    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionQuizzEntity question;

    @OneToOne( cascade = CascadeType.ALL, mappedBy = "answer")
    private CorrectAnswerQuizzEntity correctAnswerQuizz;

}
