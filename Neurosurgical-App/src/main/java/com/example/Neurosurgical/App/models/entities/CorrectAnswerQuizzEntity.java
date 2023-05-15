package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "correct_answers_quizz")
public class CorrectAnswerQuizzEntity extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "id_answer", nullable = false)
    private AnswerQuizzEntity answer;

}
