package com.example.Neurosurgical.App.models.entities;

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
@Table(name = "exam_has_questions")
public class ExamHasQuestionsEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id_exam")
    private ExamEntity exam;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private ExamQuestionEntity question;
}
