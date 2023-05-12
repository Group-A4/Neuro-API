package com.example.Neurosurgical.App.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "answers_exam")
public class AnswerExamEntity extends BaseEntity {
    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionExamEntity question;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "answer")
    private List<CorrectAnswerExamEntity> correctExamAnswer;

    public AnswerExamEntity(){
        this.correctExamAnswer = new ArrayList<>();
    }
}
