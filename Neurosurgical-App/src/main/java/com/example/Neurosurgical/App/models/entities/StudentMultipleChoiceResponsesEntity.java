package com.example.Neurosurgical.App.models.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "student_multiple_choice_responses")
public class StudentMultipleChoiceResponsesEntity extends BaseEntity {

        @ManyToOne
        @JoinColumn(name = "id_student_question_points", nullable = false)
        private StudentQuestionPointsEntity studentQuestionPoints;

        @Column(name = "id_answer", nullable = false)
        private Long idAnswer;

        public StudentMultipleChoiceResponsesEntity() {
        }
}
