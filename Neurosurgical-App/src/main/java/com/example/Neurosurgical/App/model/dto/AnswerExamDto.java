package com.example.Neurosurgical.App.model.dto;

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
public class AnswerExamDto {
    private Long idQuestion;
    private String answerText;
    private boolean isCorrect;
}