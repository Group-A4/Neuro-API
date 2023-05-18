package com.example.Neurosurgical.App.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionMultipleChoiceExamCreationDto {
    private Long idProfessor;
    private String questionText;
    private Double points;
    private List<AnswerExamCreationDto> answersQuestion;
}
