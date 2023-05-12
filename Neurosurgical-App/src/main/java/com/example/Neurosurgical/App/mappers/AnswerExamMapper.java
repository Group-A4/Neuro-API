package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.AnswerExamDto;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnswerExamMapper {
    public static AnswerExamDto toDto (AnswerExamEntity answerExamEntity,
                                       List<CorrectAnswerExamEntity> correctAnswerExamEntity) {
        return AnswerExamDto.builder()
                .idQuestion(answerExamEntity.getQuestion().getId())
                .answerText(answerExamEntity.getAnswerText())
                .isCorrect(correctAnswerExamEntity != null && correctAnswerExamEntity.stream().anyMatch(
                        correctExamAnswer -> correctExamAnswer.getAnswer().getId().equals(answerExamEntity.getId())
                ))
                .build();
    }

    public static AnswerExamEntity fromDto(AnswerExamDto answer, QuestionExamEntity questionExamEntity) {
        return AnswerExamEntity.builder()
                .answerText(answer.getAnswerText())
                .question(questionExamEntity)
                .build();
    }
}
