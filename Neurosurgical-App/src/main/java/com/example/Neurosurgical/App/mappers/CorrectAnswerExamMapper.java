package com.example.Neurosurgical.App.mappers;

import com.example.Neurosurgical.App.models.entities.AnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.CorrectAnswerExamEntity;
import com.example.Neurosurgical.App.models.entities.QuestionExamEntity;
import org.springframework.stereotype.Component;

@Component
public class CorrectAnswerExamMapper {
    public static CorrectAnswerExamEntity fromExamAnswerEntity(AnswerExamEntity answerExamEntity,
                                                               QuestionExamEntity questionExamEntity){
        return CorrectAnswerExamEntity.builder()
                .answer(answerExamEntity)
                .question(questionExamEntity)
                .build();
    }
}