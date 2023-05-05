package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.entities.CorrectExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamAnswerEntity;
import com.example.Neurosurgical.App.models.entities.ExamQuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class CorrectExamAnswerMapper {
    public static CorrectExamAnswerEntity fromExamAnswerEntity(ExamAnswerEntity examAnswerEntity,
                                                               ExamQuestionEntity examQuestionEntity) {
        return CorrectExamAnswerEntity.builder()
                .answer(examAnswerEntity)
                .question(examQuestionEntity)
                .build();
    }
}
