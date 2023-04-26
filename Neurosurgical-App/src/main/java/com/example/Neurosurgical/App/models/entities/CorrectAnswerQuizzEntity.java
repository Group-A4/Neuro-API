package com.example.Neurosurgical.App.models.entities;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_answer")
    private AnswerQuizzEntity answer;
}
