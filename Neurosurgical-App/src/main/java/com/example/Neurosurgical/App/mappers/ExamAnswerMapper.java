package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.ExamAnswerDto;
import com.example.Neurosurgical.App.models.entities.CorrectExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExamAnswerMapper {
    public static ExamAnswerDto toDto (ExamAnswerEntity examAnswerEntity,
                                       List<CorrectExamAnswerEntity> correctExamAnswerEntity) {
        return ExamAnswerDto.builder()
                .idQuestion(examAnswerEntity.getQuestion().getId())
                .answerText(examAnswerEntity.getAnswerText())
                .isCorrect(correctExamAnswerEntity != null && correctExamAnswerEntity.stream().anyMatch(
                        correctExamAnswer -> correctExamAnswer.getAnswer().getId().equals(examAnswerEntity.getId())
                ))
                .build();
    }

    public static ExamAnswerEntity fromDto(ExamAnswerDto answer, ExamQuestionEntity examQuestionEntity) {
        return ExamAnswerEntity.builder()
                .answerText(answer.getAnswerText())
                .question(examQuestionEntity)
                .build();
    }
}
