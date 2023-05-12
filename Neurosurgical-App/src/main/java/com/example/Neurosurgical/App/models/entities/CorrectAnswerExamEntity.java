package com.example.Neurosurgical.App.models.entities;

import lombok.*;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "correct_answers_exam")
public class CorrectAnswerExamEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionExamEntity question;

    @ManyToOne
    @JoinColumn(name = "id_answer")
    private AnswerExamEntity answer;
}
