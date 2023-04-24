package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
