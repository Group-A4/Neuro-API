package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionLongResponseExamResultDto {
    private Long id;
    private Long idExam;
    private Long idProfessor;
    private String questionText;
    private Double points;
    private Double studentPoints;
    private String expectedResponse;
    private String studentResponse;
}
