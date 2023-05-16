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
@Table(name = "student_long_responses")
public class StudentLongResponsesEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_student_question_points")
    private StudentQuestionPointsEntity studentQuestionPoints;

    @Column(name = "student_response")
    private String studentResponse;

    public StudentLongResponsesEntity() {
    }
}
