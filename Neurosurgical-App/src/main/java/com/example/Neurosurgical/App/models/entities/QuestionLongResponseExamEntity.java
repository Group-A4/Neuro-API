package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "long_response_questions")
public class QuestionLongResponseExamEntity {
    @Id
    @OneToOne
    @JoinColumn(name = "id_question")
    private QuestionExamEntity question;

    @Column(name = "expected_response")
    private String expectedResponse;
}
