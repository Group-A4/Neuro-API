package com.example.Neurosurgical.App.model.entity;

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
@Table(name = "quizz_has_questions")

public class QuizzHasQuestionsEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_quizz")
    private QuizzEntity quizz;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionQuizzEntity question;

}
