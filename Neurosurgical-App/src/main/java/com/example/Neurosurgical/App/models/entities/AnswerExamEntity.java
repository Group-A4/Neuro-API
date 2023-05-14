package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "answer")
    private CorrectAnswerExamEntity correctAnswerExam;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AnswerExamEntity that = (AnswerExamEntity) o;
        return Objects.equals(question, that.question) && Objects.equals(answerText, that.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), question, answerText);
    }
}
