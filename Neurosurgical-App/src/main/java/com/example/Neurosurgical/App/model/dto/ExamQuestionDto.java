package com.example.Neurosurgical.App.model.dto;

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
public class ExamQuestionDto {
    private Long idCourse;
    private Long idProfessor;
    private String questionText;
    private List<AnswerExamDto> answersQuestions;
}
