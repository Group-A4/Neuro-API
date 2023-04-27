package com.example.Neurosurgical.App.model.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "correct_answers_quizz")
public class CorrectAnswerQuizzEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionQuizzEntity question;

    @ManyToOne
    @JoinColumn(name = "id_answer")
    private AnswerQuizzEntity answer;
}