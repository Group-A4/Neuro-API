package com.example.Neurosurgical.App.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "correct_answers_exam")
public class CorrectExamAnswerEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "id_question")
    private ExamQuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "id_answer")
    private ExamAnswerEntity answer;
}