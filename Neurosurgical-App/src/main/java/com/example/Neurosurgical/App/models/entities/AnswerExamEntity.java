package com.example.Neurosurgical.App.models.entities;

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
@Table(name = "answers_exam")
public class AnswerExamEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionExamEntity question;

    @Column(name = "answer_text")
    private String answerText;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    private List<CorrectAnswerExamEntity> correctAnswersQuizz;

    public AnswerExamEntity() {
        this.correctAnswersQuizz = new ArrayList<>();
    }
}
