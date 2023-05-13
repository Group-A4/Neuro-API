package com.example.Neurosurgical.App.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "long_response_question")
public class LongResponseQuestionEntity {
    @Id
    private Long idQuestion;

    @Column(name = "expected_response")
    private String expectedResponse;
}
