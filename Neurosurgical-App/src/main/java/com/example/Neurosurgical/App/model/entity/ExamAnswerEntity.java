package com.example.Neurosurgical.App.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "answers_exam")
public class ExamAnswerEntity extends BaseEntity{
    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private ExamQuestionEntity question;

    @JsonIgnore
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "answer")
    private List<CorrectExamAnswerEntity> correctExamAnswer;

    public ExamAnswerEntity(){
        this.correctExamAnswer = new ArrayList<>();
    }

}
