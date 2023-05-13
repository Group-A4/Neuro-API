package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answers_exam")
public class AnswerExamEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionExamEntity question;

    @Column(name = "answer_text")
    private String answerText;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "answer")
    private CorrectAnswerExamEntity correctAnswerExam;

}
