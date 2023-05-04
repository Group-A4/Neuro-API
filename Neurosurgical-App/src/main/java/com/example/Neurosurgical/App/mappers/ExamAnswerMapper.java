package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.dtos.ExamAnswerDto;
import com.example.Neurosurgical.App.models.entities.CorrectExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamAnswerEntity;
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
}
