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
public class QuestionQuizzDto {
    private Long idCourse;
    private Long idProfessor;
    private String questionText;
    private List<AnswerQuizzDto> answersQuestion;
}
